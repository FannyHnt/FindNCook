import io
import sys
from matplotlib import pyplot as plt
import streamlit as st
import pandas as pd
import numpy as np
from lifelines import KaplanMeierFitter, NelsonAalenFitter, CoxPHFitter
from lifelines.statistics import logrank_test, pairwise_logrank_test
import plotly.graph_objs as go

# Configuration de la page
st.set_page_config(layout="wide", page_title="Analyse de Survie")

# Fonction pour charger les données
@st.cache_data
def load_data(file, encoding):
    data = pd.read_csv(file, encoding=encoding)
    return data

# Fonction pour créer des tranches d'âge et de BMI
def create_tranches(data):
    data['Tranche_Age'] = pd.cut(data['Age'], bins=[0, 50, 60, np.inf], labels=['<50', '50-60', '>60'])
    data['Tranche_BMI'] = pd.cut(data['BMI'], bins=[0, 18, 26, np.inf], labels=['<18', '18-26', '>26'])
    return data

# Fonction pour gérer les doublons (adaptée pour ne pas dépendre de 'Patient_ID')
def remove_duplicates(data):
    data = data[~((data.duplicated()) & (data['Event_Observed'] == 0))]
    return data

# Fonction pour nettoyer les données
def gestion_donnees_manquantes(data):
    # Calcul des données manquantes par colonne
    missing_data = data.isna().sum()
    total_rows = len(data)
    missing_percentage = (missing_data / total_rows) * 100  # Calcul du pourcentage de données manquantes par colonne
    results = []
    # Supprimer les colonnes avec plus de 20% de données manquantes
    cols_to_drop = missing_percentage[missing_percentage > 20].index
    initial_cols = data.shape[1]
    data.drop(columns=cols_to_drop, inplace=True)
    final_cols = data.shape[1]
    results.append(initial_cols - final_cols)
    
    # Supprimer les lignes avec des données manquantes restantes
    initial_rows = len(data)
    data.dropna(inplace=True)
    final_rows = len(data)
    results.append(initial_rows - final_rows)
    results.append(data)
    return results

# Chargement des données
st.sidebar.header("Chargement des données")
uploaded_file = st.sidebar.file_uploader("Choisissez un fichier CSV", type="csv")
encoding = st.sidebar.selectbox("Choisissez l'encodage du fichier", options=["utf-8", "latin-1", "ascii"], index=0)

if uploaded_file:
    data = load_data(uploaded_file, encoding)
    # Vérifiez et supprimez les lignes avec des NaN dans les colonnes 'Time_to_Event' et 'Event_Observed' 
    if data[['Time_to_Event', 'Event_Observed']].isna().any().any():
        data = data.dropna(subset=['Time_to_Event', 'Event_Observed'])

    if 'Time_to_Event' in data.columns and 'Event_Observed' in data.columns:
        data = create_tranches(data)
        data = remove_duplicates(data)

        data_with_missing = data.copy()
        resulat_gestion_donnees_manquantes = gestion_donnees_manquantes(data)
        data = resulat_gestion_donnees_manquantes[2]

        liste_options = data.columns.tolist()
        liste_options.remove('Time_to_Event')
        liste_options.remove('Event_Observed')
        liste_options.remove('Age')
        liste_options.remove('BMI')

        # Barre latérale pour la sélection des variables et des sous-groupes
        st.sidebar.header("Filtrage des données")
        st.sidebar.subheader("Sélection des variables")

        # Sélection des variables à inclure dans l'analyse
        variables_selectionnees = st.sidebar.multiselect(
            "Choisissez les variables à inclure dans l'analyse",
            options=liste_options,
            default=liste_options,
        )

        # Filtrage des sous-groupes d'individus
        st.sidebar.subheader("Filtrage des sous-groupes")
        sous_groupes = {}
        for col in data.columns:
            if data[col].dtype in ['object', 'category'] or len(data[col].unique()) < 10:  # Variables catégoriques ou avec peu de valeurs uniques
                valeurs = st.sidebar.multiselect(
                    f"Filtrer par {col}",
                    options=data[col].unique(),
                    default=data[col].unique()
                )
                sous_groupes[col] = valeurs

        # Application des filtres
        if sous_groupes:
            for col, valeurs in sous_groupes.items():
                data = data[data[col].isin(valeurs)]

        # Mise à jour des options disponibles pour les graphiques
        liste_options = [col for col in variables_selectionnees if col not in ['Time_to_Event', 'Event_Observed', 'Age', 'BMI']]



        # Onglets principaux
        tabs = st.tabs(["Visualisation des données", "Traitement des données manquantes", " Statistiques descriptives", "Représentation graphique des variables",
                        "Probabilité de survie et courbe de survie" ,"Prédiction de survie d'un individu", "Modèle de Cox"])

        # Visualisation des données
        with tabs[0]:
            st.header("Visualisation des données")
            st.write(data_with_missing)

        # Traitement des données manquantes
        with tabs[1]:
            st.header("Traitement des données manquantes")
            missing_data = data_with_missing.isna().sum()
            total_rows = len(data_with_missing)
            missing_percentage = (missing_data / total_rows) * 100  # Calcul du pourcentage de données manquantes par colonne

             
            st.write("Données manquantes par colonne :")
            st.write(pd.DataFrame({
                "Nombre de valeurs manquantes": missing_data,
                "Pourcentage de valeurs manquantes (%)": missing_percentage
            }))
            
            if st.button("Traiter les données manquantes"):

                st.write(f"{resulat_gestion_donnees_manquantes[0]} colonnes contenant plus de 20 % de données manquantes ont été supprimé")
                st.write(f"{resulat_gestion_donnees_manquantes[1]} lignes contenant des données manquantes ont été supprimé ")
                
                # Afficher les données après traitement
                st.write("Données après traitement des données manquantes :")
                st.write(data)

        # Statistiques descriptives
        with tabs[2]:
            st.header("Statistiques descriptives")
            st.subheader("Statistiques générales")
            st.write(data.describe(include='all'))  # Affiche les statistiques descriptives pour toutes les colonnes

            st.subheader("Distribution des variables catégoriques")
            categorical_columns = data.select_dtypes(include=['object', 'category']).columns
            for col in categorical_columns:
                st.write(f"Distribution pour la variable {col}:")
                st.write(data[col].value_counts())

        # Représentation graphique des variables
        with tabs[3]:
            st.header("Représentation graphique des variables")
            
            # Sélection de la variable à représenter
            variable = st.selectbox("Choisissez une variable à représenter", options=liste_options)


            # Graphique pour les variables catégoriques
            st.subheader(f"Diagramme en barres pour {variable}")
            fig, ax = plt.subplots()
            data[variable].value_counts().plot(kind='bar', ax=ax, color='skyblue', edgecolor='black')
            ax.set_title(f"Distribution de {variable}")
            ax.set_xlabel(variable)
            ax.set_ylabel("Fréquence")
            st.pyplot(fig)

        # Analyse de survie avec Kaplan-Meier
        with tabs[4]:
            st.header("Analyse de survie avec Kaplan-Meier et estimation du risque")

            # Vérifiez que les colonnes nécessaires sont présentes
            if 'Time_to_Event' in data.columns and 'Event_Observed' in data.columns:
                kmf = KaplanMeierFitter()

                # Ajustement du modèle Kaplan-Meier
                kmf.fit(data['Time_to_Event'], event_observed=data['Event_Observed'])

                # 1. Estimation de la probabilité de survie et de l'intervalle de confiance
                st.subheader("Estimation de la probabilité de survie")
                st.write("Probabilité de survie et intervalle de confiance :")
                survival_table = kmf.survival_function_.join(kmf.confidence_interval_)
                st.write(survival_table)

                # 2. Affichage du tableau des proportions de survivants à chaque instant t
                st.subheader("Tableau des proportions de survivants")
                st.write("Proportions de survivants à chaque instant t :")
                st.write(kmf.event_table)

                # 3. Traçage de la courbe de survie globale avec intervalle de confiance
                st.subheader("Courbe de survie globale")
                fig = go.Figure()
                fig.add_trace(go.Scatter(
                    x=kmf.survival_function_.index,
                    y=kmf.survival_function_['KM_estimate'],
                    mode='lines',
                    name='Survival Function'
                ))
                fig.add_trace(go.Scatter(
                    x=kmf.confidence_interval_.index,
                    y=kmf.confidence_interval_['KM_estimate_upper_0.95'],
                    mode='lines',
                    line=dict(dash='dot'),
                    name='Upper Confidence Interval'
                ))
                fig.add_trace(go.Scatter(
                    x=kmf.confidence_interval_.index,
                    y=kmf.confidence_interval_['KM_estimate_lower_0.95'],
                    mode='lines',
                    line=dict(dash='dot'),
                    name='Lower Confidence Interval'
                ))
                fig.update_layout(
                    title="Courbe de survie globale avec intervalle de confiance",
                    xaxis_title="Temps",
                    yaxis_title="Probabilité de survie"
                )
                st.plotly_chart(fig)

                # 4. Comparaison des courbes de survie en fonction d'un critère
                st.subheader("Comparaison des courbes de survie par groupe")
                group_var = st.selectbox("Sélectionnez une variable pour comparer les groupes", options=liste_options, key="group_var_survival")
                if group_var:
                    fig = go.Figure()
                    for group in data[group_var].unique():
                        group_data = data[data[group_var] == group]
                        kmf_group = KaplanMeierFitter()
                        kmf_group.fit(group_data['Time_to_Event'], event_observed=group_data['Event_Observed'], label=str(group))
                        
                        # Récupérer dynamiquement le nom de la colonne
                        survival_column = kmf_group.survival_function_.columns[0]
                        
                        fig.add_trace(go.Scatter(
                            x=kmf_group.survival_function_.index,
                            y=kmf_group.survival_function_[survival_column],
                            mode='lines',
                            name=f"{group_var} = {group}"
                        ))
                    fig.update_layout(
                        title=f"Courbes de survie par {group_var}",
                        xaxis_title="Temps",
                        yaxis_title="Probabilité de survie"
                    )
                    st.plotly_chart(fig)

                    # 5. Test de comparaison des fonctions de survie (Log-rank test)
                    st.subheader("Test de comparaison des fonctions de survie (Log-rank test)")
                    try:
                        # Vérifiez que la variable sélectionnée est catégorique et contient au moins deux groupes
                        if data[group_var].nunique() > 1:
                            groups = data[group_var].unique()
                            if len(groups) == 2:
                                # Comparaison entre deux groupes
                                group1 = data[data[group_var] == groups[0]]
                                group2 = data[data[group_var] == groups[1]]
                                results = logrank_test(
                                    group1['Time_to_Event'], group2['Time_to_Event'],
                                    event_observed_A=group1['Event_Observed'], event_observed_B=group2['Event_Observed']
                                )
                                st.write(f"Résultat du test log-rank entre les groupes {groups[0]} et {groups[1]} :")
                                st.write(f"p-valeur : {results.p_value:.4f}")
                                if results.p_value < 0.05:
                                    st.write("Conclusion : Les fonctions de survie des deux groupes sont significativement différentes.")
                                else:
                                    st.write("Conclusion : Aucune différence significative entre les fonctions de survie des deux groupes.")
                            else:
                                # Comparaison entre plusieurs groupes
                                results = pairwise_logrank_test(
                                    data['Time_to_Event'], data[group_var], event_observed=data['Event_Observed']
                                )
                                st.write("Résultats des tests log-rank pairwise entre les groupes :")
                                st.write(results.summary)
                        else:
                            st.warning("La variable sélectionnée doit contenir au moins deux groupes distincts pour effectuer le test log-rank.")
                    except Exception as e:
                        st.error(f"Erreur lors du test log-rank : {e}")

        # Prédiction de survie
        with tabs[5]:
            st.header("Prédiction de survie")
            # Estimation de la fonction de risque cumulée avec Nelson-Aalen Fitter
            st.subheader("Estimation de la fonction de risque cumulée (Cumulative Hazard Function)")
            naf = NelsonAalenFitter()
            naf.fit(data['Time_to_Event'], event_observed=data['Event_Observed'])

            # Tracer la courbe du risque cumulé
            fig, ax = plt.subplots(figsize=(10, 6))
            naf.plot_cumulative_hazard(ax=ax, color='blue', label='Cumulative Hazard')
            ax.set_title("Courbe du risque cumulé (Cumulative Hazard Function)")
            ax.set_xlabel("Temps")
            ax.set_ylabel("Risque cumulé")
            st.pyplot(fig)

            # Prédiction de survie avec Nelson-Aalen Fitter
            st.subheader("Prédiction de survie avec Nelson-Aalen Fitter ([ S(t) = e^{-H(t)} ])")
            time_input = st.number_input("Entrez un temps (en jours)", min_value=0.0, value=30.0, step=1.0, key="time_input_nelson_aalen")

            if st.button("Estimer la probabilité de survie avec Nelson-Aalen", key="button_nelson_aalen"):
                # Calcul de la fonction de risque cumulée pour le temps donné
                cumulative_hazard = naf.cumulative_hazard_at_times([time_input]).values[0]
                
                # Estimation de la probabilité de survie
                survival_prob = np.exp(-cumulative_hazard)
                
                st.write(f"Probabilité de survie à {time_input} jours (estimée avec Nelson-Aalen) : {survival_prob:.2f}")

        # Modèle de Cox
        with tabs[6]:
            st.header("Modèle de régression de Cox")

            # Sélection des covariables
            cox_variables = st.multiselect(
                'Sélectionnez les covariables pour le modèle de Cox',
                options=liste_options,
                default=liste_options
            )

            if cox_variables:
                try:
                    # Ajustement du modèle de Cox
                    cph = CoxPHFitter()
                    cph.fit(data, duration_col='Time_to_Event', event_col='Event_Observed', formula=' + '.join(cox_variables))

                    # Affichage des paramètres du modèle
                    st.subheader("Paramètres du modèle de Cox :")
                    st.write(cph.params_)

                    # Affichage des p-valeurs
                    st.subheader("p-valeurs :")
                    p_values = cph.summary.p
                    tmp_df = pd.DataFrame(p_values)
                    tmp_df['Significance'] = np.where(tmp_df['p'] < 0.05, 'Impact significatif sur la survie', 'Pas d’impact significatif sur la survie')
                    st.write(tmp_df)

                    # Affichage des Hazard Ratios
                    st.subheader("Hazard Ratios :")
                    hazard_ratios = cph.hazard_ratios_
                    st.write(hazard_ratios)

                    # Variable ayant le plus grand impact
                    st.write(f"La variable ayant le plus grand impact sur la survie est : **{list(hazard_ratios.nlargest(1).index)[0]}**")

                    # Résultats détaillés
                    st.subheader("Résultats détaillés :")
                    st.write(cph.summary)

                    # Vérification des hypothèses du modèle de Cox
                    st.subheader("Vérification des hypothèses du modèle de Cox")

                    try:

                        # Capture des messages de check_assumptions
                        buffer = io.StringIO()
                        sys.stdout = buffer  # redirige les sorties vers le buffer
                        cph.check_assumptions(data, p_value_threshold=0.05, show_plots=False)
                        sys.stdout = sys.__stdout__  # remet la sortie normale

                        # Lire ce qui a été écrit
                        output = buffer.getvalue()

                        # Vérifier si des alertes sont présentes
                        if "violates the proportional hazard assumption" in output.lower():
                            st.warning("Hypothèse non respectée pour au moins une variable.")
                        else:
                            st.success("Toutes les hypothèses sont respectées.")

                        # Vérification des hypothèses avec un seuil de p-valeur de 0.05
                        #cph.check_assumptions(data, p_value_threshold=0.05, show_plots=True)

                        # Afficher un message si les hypothèses sont satisfaites
                        #st.success("Les hypothèses de proportionnalité des risques ont été vérifiées.")
                    except Exception as e:
                        # Gestion des erreurs
                        st.error(f"Erreur lors de la vérification des hypothèses : {e}")
                        
                    # Visualisation des courbes de survie ajustées
                    st.subheader("Courbes de survie ajustées")

                    variable_to_plot = st.selectbox(
                        "Sélectionnez une variable pour visualiser les courbes de survie ajustées",
                        options=cox_variables,
                        key="cox_variable_plot"
                    )

                    if variable_to_plot:
                        try:
                            # Vérification que la variable sélectionnée est valide
                            if variable_to_plot not in data.columns:
                                st.warning(f"La variable sélectionnée ({variable_to_plot}) n'est pas valide.")
                            else:
                                # Tracer les courbes de survie ajustées
                                fig, ax = plt.subplots(figsize=(10, 6))
                                cph.plot_partial_effects_on_outcome(
                                    covariates=variable_to_plot,
                                    values=data[variable_to_plot].unique(),
                                    ax=ax
                                )
                                ax.set_title(f"Courbes de survie ajustées pour {variable_to_plot}")
                                ax.set_xlabel("Temps")
                                ax.set_ylabel("Probabilité de survie")
                                st.pyplot(fig)
                        except Exception as e:
                            st.error(f"Erreur lors de la visualisation des courbes de survie ajustées : {e}")

                except Exception as e:
                    st.error(f"Erreur lors de l'ajustement du modèle de Cox : {e}")
            else:
                st.warning("Veuillez sélectionner au moins une covariable pour ajuster le modèle de Cox.")
    else:
        st.error("Les colonnes 'Time_to_Event' et 'Event_Observed' sont nécessaires pour l'analyse de survie.")
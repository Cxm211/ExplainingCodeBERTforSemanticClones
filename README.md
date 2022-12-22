# Explaining CodeBERT for Semantic Clones


This GitHub repo contains the source code, data, and results from our research study on explaining the predictions of the CodeBERT model for semantic code clone detection.

Data files include 100 clone pair samples of Java and 100 clone pair samples of Python which we filter from the SemanticCloneBench dataset of semantic clones. The human labels for clone pairs and statements are also uploaded as data.

Results include spreadsheets and files containing results of mislabeling analysis, misprediction analysis, correlation analysis, intersection analysis, and statement types analysis.

Code includes the code for filtering the clone pairs, wheat culprit interpretation, and SHAP interpretation. We have also uploaded the code that performs the human-machine interpretation correlation analysis and statement types analysis. Some helper code to aid the analysis is also uploaded.

The Java folder contains all data, code, and results for the Java clone pairs. For further instructions on how to replicate please refer to README inside the Java folder.
The Python folder contains all data, code, and results for the Python clone pairs. For further instructions on how to replicate please refer to README inside the Python folder.



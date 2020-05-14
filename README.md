# Covid-19 Knowledge Graph Analysis
The knowledge graph analysis for Covid-19 related data


* Summary of today's (2020.05.13) meeting:

Networks we have:
Two in-house systems of the knowledge graph.
The human protein-protein interactome provided in Github (https://github.com/ChengF-Lab/GPSnet)

What we might do:
Rank the relationship (Nature paper: based on distance)
Discover new relationships (Drug-Drug combination or Drug-Protein)

New repurposing method
Repurposing based on the two in-house networks
Multi-drug effects (Nature paper: can only support pairwise drug combinations)
Confirmation of existing relationship (Based on the nature paper or other literature)
Drug efficiency and side effects (relates to gender, age, etc.)
Add more information to the existing graphs (Candidate nodes: symptoms, treatments, protein domains, virus proteins, and virus protein-related drugs)

To-do:
Vincent:
Provide a list of drugs (Drugbank ID) that is related to the COVID-19.
(Not discussed in the meeting. It just comes to my mind): do a literature review on COVID-19 drug efficiency and side effects (relates to gender, age, etc.) and see if there is any database available.
Xiaodong:
Investigate the human protein-protein interactome provided in Github (https://github.com/ChengF-Lab/GPSnet).
Based on the available networks and the list of drugs provided by Vincent: gather information for the next time discussion.
Ou Min:
Investigate the databases that can provide the candidate node information.
Design new motifs based on the available nodes. Figure out what kinds of questions we can answer based on the motifs.

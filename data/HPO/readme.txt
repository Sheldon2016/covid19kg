#file_name, sources
drug_virus.txt, openKG[http://openkg1.oss-cn-beijing.aliyuncs.com/46838001-9a2f-4edb-8718-091135bf21d6/virusnetwokr.drug2.27.json, http://openkg1.oss-cn-beijing.aliyuncs.com/af892745-1a30-4068-b0cf-2d8b7d296ab0/virusnetwork.extract-paper.json], nature paper data to be imported
drug_virusprotein.txt, openKG[http://openkg1.oss-cn-beijing.aliyuncs.com/af892745-1a30-4068-b0cf-2d8b7d296ab0/virusnetwork.extract-paper.json]
geneID_hostprotein.txt, nature paper [table s3], NCBI records
gene_disease.txt, [https://www.disgenet.org/static/disgenet_ap1/files/downloads/all_gene_disease_pmid_associations.tsv.gz]
hpoID_diseaseID_disease.txt, HPO links
hpoID_geneID.txt, HPO links
virus_disease.txt, openKG[http://openkg1.oss-cn-beijing.aliyuncs.com/af892745-1a30-4068-b0cf-2d8b7d296ab0/virusnetwork.extract-paper.json], HPO data to be inferred (via the path virus-hostprotein-gene-symptom-disease)
virus_hostprotein.txt, nature paper
virus_virusprotein.txt, openKG[http://openkg1.oss-cn-beijing.aliyuncs.com/46838001-9a2f-4edb-8718-091135bf21d6/virusnetwokr.drug2.27.json, http://openkg1.oss-cn-beijing.aliyuncs.com/af892745-1a30-4068-b0cf-2d8b7d296ab0/virusnetwork.extract-paper.json, http://openkg1.oss-cn-beijing.aliyuncs.com/a55697d1-35c2-4d9e-ba45-e9bf083b4623/virusnetwork.sars-cov-22.27.json]
virusprotein_hostprotein.txt, openKG[http://openkg1.oss-cn-beijing.aliyuncs.com/46838001-9a2f-4edb-8718-091135bf21d6/virusnetwokr.drug2.27.json, http://openkg1.oss-cn-beijing.aliyuncs.com/af892745-1a30-4068-b0cf-2d8b7d296ab0/virusnetwork.extract-paper.json]

# Extension from Chris

We first extract four labels (drugbank-ID, drug-name, description and indication) from DrugBank. Then we use the HPO database to extract two labels (disease-ID, disease-name) from phenotype_annotation of HPO database. We further match each disease-name with description and indication of each drug in DrugBank to create the “drug-disease.txt” edge file.
 
For the “drug-symptom” edge file, we extract the same four labels from DrugBank and two labels (HPO-ID, symptom-name) from hp.obo of HPO database. Therefore symptom-name could be watched with description and indication of each drug in of DrugBank and “drug-symptom.txt” edge file is created.
 
Protein-protein interaction information is extracted in combined with two databases: Uniprot and Biogrid. We first map the coronavirus-related protein of Uniprot with Biogrid databases using Uniprot ID to produce the whole SARS-CoV and SARS-CoV-2 protein information with NCBI-gene ID which is “virusprotein.txt” node file. Taxonomy information is extracted from NCBI. Then we filter every protein-protein interaction either related to SARS-CoV or SARS-CoV-2 from Biogrid. Interactions between SARS-CoV and SARS-CoV-2 or themselves were used to create “virusprotein-virusprotein.txt” edge file. Interactions between human with SARS-CoV or SARS-CoV-2 were used to create “virusprotein-hostprotein.txt” edge file.

Now we have 68536	entities and 1722806 relations.

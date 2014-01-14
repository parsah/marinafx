# Introduction
Marina is a Java GUI-only software tool for probabilistically contrasting transcription
factor binding sites (TFBS) frequencies between two sets of promoter sequences. 
It is designed for the investigator to conduct analyses around the identification of statistically-sound and over-representative TFBSs. 
We show that Marina is capable of identifying more over-represented TFBSs compared
to a popular software tool, regardless of the size of promoter sequence sets.

Marina requires 2x FASTA files; one being a set of control promoter sequences and the other
being the query set of sequences. Furthermore, Marina requires a catalog of known TFBSs which get
mapped onto both sets of sequences.
These TFBS models can be modeled as either position weight matrices (PWM) or linear
DNA sequences, DNA motifs.
The schema as to how these TFBS models must be structured is outlined in the documentation.

Once a catalog of TFBS models and promoter sequences have been provided, 
Marina contrasts TFBS abundances across these various sequence-sets and deduces
degree of over-representation using 7 knowledge-discovery metrics. 
Each metric ranks all identified TFBSs with intent of assisting the investigator
determining the most over-represented binding sites. Oftentimes, differing metrics rank
TFBSs differently. To capture the most over-represented TFBSs, Marina uses the
Iterative Proportional Fitting (IPF) algorithm to facilitate unanimous "agreement" across all metrics.

# Prerequisites
1. Java 7 or greater <http://www.java.com/en/>

# Questions and Contact
We encourage users to contact us should you have questions or concerns about this tool.

Please direct all questions to: Parsa Hosseini <parsa.hosseini@nih.gov>

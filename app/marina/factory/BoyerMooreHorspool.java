package marina.factory;

import java.io.IOException;
import java.util.ArrayList;

import marina.bindingsite.LinearDNAMotif;
import marina.group.FASTASequence;

public class BoyerMooreHorspool {
	
	public void align(FASTASequence seq, LinearDNAMotif motif) throws IOException {
		if (seq.getSequence().equals("") || seq == null ||
				motif.getSequence().equals("") || motif == null) {
			throw new IOException("FASTA entries must have sequences.");
		}
		int lengthPattern = motif.getLength();
		int lengthSequence = seq.getLength();
		if (lengthPattern <= lengthSequence) { // sequence must be large
			int[] skip = new int[256]; // for skipping certain segments
			for (int i = 0; i < skip.length; i++) {
				skip[i] = lengthPattern;
			}
			for (int index = 0; index < lengthPattern - 1; index++) {
				char c = motif.getSequence().charAt(index);
				skip[(int)c] = lengthPattern - index-1;
			}
			int index = lengthPattern - 1;
			while (index < lengthSequence) {
				int j = lengthPattern - 1;
				int i = index; // start at the right
				char charFASTA = seq.getSequence().charAt(i);
				char charMotif = motif.getSequence().charAt(j);
				while (j >= 0 && charFASTA == charMotif) { // query each char
					j -= 1;
					i -= 1; // move-up the FASTA sequence
				}
				if (j == -1) { // exact-match found; add to sequence
					if (!seq.getMappings().containsKey(motif)) {
						seq.getMappings().put(motif, new ArrayList<Integer>());
					} // lastly, add mapped-motif to its respective sequence
					seq.getMappings().get(motif).add(i+1);
				}
				index += skip[(int)charFASTA];
			}
		}
	}
	
}
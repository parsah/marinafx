package marina.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import marina.bindingsite.LinearDNAMotif;
import marina.group.FASTASequence;

public class BoyerMooreHorspool {

	public void align(FASTASequence seq, LinearDNAMotif motif) throws IOException {
		if (seq.getSequence().equals("") || seq == null ||
				motif.getSequence().equals("") || motif == null) {
			throw new IOException("FASTA entries must have sequences.");
		}
		int m = motif.getLength();
		int n = seq.getLength();
		List<Integer> indices = new ArrayList<Integer>();
		int[] skip = new int[256]; // for skipping certain segments
		for (int i = 0; i < skip.length; i++) {
			skip[i] = m;
		}
		for (int index = 0; index < (m - 1); index++) {
			char c = motif.getSequence().charAt(index);
			skip[(int)c] = m - index-1;
		}
		int k = m - 1;
		// TODO revise Boyer-Moore-Horspool algorithm.
//		while (k < n) {
//			int j = (m - 1);
//			int i = k; // start at the right
//			char charseq.getSequence().indexOf(i);
//			char charMotif = motif.getSequence().charAt(j);
//			//			System.out.println(i+" "+ j);
//			while (j >= 0 && (charFASTA == charMotif)) { // query each char
//				j -= 1;
//				i -= 1; // move-up the FASTA sequence
//
//				//			System.out.println(i+" " + j);
//			}
//			if (j == -1) { // exact-match found; add to sequence
//				//				System.out.println((i+1));
//				System.out.println((i+1));
//			}
//			k += skip[(int)seq.getSequence().charAt(k)];
//		}
	}
}

package alignment;

import group.FASTASequence;

import java.io.IOException;
import java.util.ArrayList;

import bindingsite.LinearDNAMotif;


public class RabinKarp {  
	private static final long RADIX = Character.MAX_VALUE + 1; // radix
	private static final long PRIME = 7; // RADIX * (PRIME + 1) < integer

	/**
	 * Execute the Rabin-Karp algorithm given a FASTASequence and a specific
	 * LinearDNAMotif object. The former represents the string whereas the
	 * latter represents the pattern.
	 * Due to the hash-based nature of Rabin-Karp, a preset prime number and
	 * radix are set; used to compute substring and actual string hashes.
	 * @throws IOException 
	 * */
	public void align(FASTASequence seq, LinearDNAMotif motif) throws IOException {
		// create empty array to prevent null-pointer exceptions later-on.
		char[] fastaList = seq.getSequence().toCharArray();
		char[] motifList = motif.getSequence().toCharArray();
		int motifLen = motifList.length;
		int fastaLen = fastaList.length;
		if (motifLen > fastaList.length)  {
			throw new IOException("DNA motifs cannot be larger than FASTA");
		}
		long patternHash = 0; // for computing hash.
		long seqHash = 0;
		// small radix and primes can prevent overflow exceptions.
		for (int i = 0; i < motifLen; i++) {
			patternHash = (patternHash * RabinKarp.RADIX + motifList[i]) % 
					RabinKarp.PRIME; 
			seqHash = (seqHash * RabinKarp.RADIX + fastaList[i]) % 
					RabinKarp.PRIME;
		}
		// compute the modular-power
		long modPow = this.modularPower(RabinKarp.RADIX, motifLen-1,
				RabinKarp.PRIME);
		int diff = fastaLen - motifLen;
		for (int offset = 0; offset <= diff; offset++) {
			if (patternHash == seqHash) {
				if (isEqual(fastaList, motifList, offset))  {
					// motif is found in the sequence; add its offset.
					if (!seq.getMappings().containsKey(motif)) {
						seq.getMappings().put(motif, new ArrayList<Integer>());
					}
					seq.getMappings().get(motif).add(offset);
				}
			}
			if (offset < diff) {
				seqHash -= modPow * fastaList[offset];
				while (seqHash < 0) {
					seqHash += RabinKarp.PRIME;
				}
				seqHash = (RabinKarp.RADIX * seqHash + 
						fastaList[offset + motifLen]) % RabinKarp.PRIME;
			}
		}
	}

	/**
	 * Computes the modular-power given a base, exponent and modulo.
	 * @return modular-power.
	 * */
	public long modularPower(long base, long exponent, long mod) {
		long result = 1;
		while (exponent > 0) {
			if (exponent % 2 == 1) {
				result = (result * base) % mod;
			}
			exponent = exponent >> 1;
			base = (base * base) % mod;
		}
		return result;
	}

	/**
	 * Given a character-array of sequence and motifs, test to see whether
	 * they equal one-another at a specific offset (index).
	 * @return boolean whether a string and specific pattern equal each other.
	 * */
	private boolean isEqual(char[] seq, char[] motif, int offset) {
		if (seq.length - offset < motif.length) {
			return false;
		}
		for (int i = 0; i < motif.length; i++)  {
			if (seq[i + offset] != motif[i])  {
				return false;
			}
		}
		return true;
	}
}

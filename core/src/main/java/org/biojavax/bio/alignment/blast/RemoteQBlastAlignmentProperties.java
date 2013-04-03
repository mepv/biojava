/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 */

package org.biojavax.bio.alignment.blast;

import java.util.HashMap;
import java.util.Set;
import java.util.Arrays;

import org.biojava.bio.BioException;
import org.biojavax.bio.alignment.RemotePairwiseAlignmentProperties;

/**
 * 
 * This class implements RemotePairwiseAlignmentProperties by specifying several
 * convenient methods used to wrap the addition of Blast alignment parameters.
 * 
 * @author Sylvain Foisy, Diploide BioIT
 * @since 1.8
 *
 */
public class RemoteQBlastAlignmentProperties implements
		RemotePairwiseAlignmentProperties {

	private static final long serialVersionUID = 1L;
	private HashMap<String, String> param = new HashMap<String, String>();

	/**
	 * This method returns the value of the program used for this particular
	 * blast run.
	 * 
	 * @return program :the name of the blastall program used.
	 */
	public String getBlastProgram() {
		return param.get("PROGRAM");
	}

	/**
	 * This method set the program to be use with blastall. This method does a
	 * validation before running on the valid blastall programs: blastn / blastp
	 * / blastx / tblastn / tblastx
	 * 
	 * @param program
	 *            : one of blastall programs
	 * @exception BioException
	 *                if the named program is not a valid blastall options
	 * 
	 */
	public void setBlastProgram(String program) throws BioException {

		boolean isValid = false;
		String[] blastPr = new String[]{ "blastn", "blastp", "blastx", "tblastn", "tblastx" };

//		for (int i = 0; i < blastPr.length; i++) {
//			if (program.equals(blastPr[i])) {
//				this.param.put("PROGRAM", program);
//				isValid = true;
//			}
//		}

		if(Arrays.binarySearch(blastPr,program)>=0){
			isValid = true;			
		}
		
		if (!isValid) {
			throw new BioException(
					"Invalid blastall program selection! Use one of valid values: blastn/blastp/blastx/tblastn/tblastx");
		}
	}

	/**
	 * This method returns the value of the database used for this particular
	 * blast run.
	 * 
	 * @return db :the name of the database used
	 */
	public String getBlastDatabase() {
		return param.get("DATABASE");
	}

	/**
	 * This method set the database to be use with blastall
	 * 
	 * @param db :a valid name to a NCBI Blastable database
	 */
	public void setBlastDatabase(String db) {
		this.param.put("DATABASE", db);
	}
	/**
	 * <p>
	 * This method is to be used if a request is to use non-default values at
	 * submission. According to QBlast info, the accepted parameters for PUT
	 * requests are:
	 * </p>
	 * 
	 * <ul>
	 * <li>-G: cost to create a gap. Default = 5 (nuc-nuc) / 11 (protein) /
	 * non-affine for megablast</li>
	 * <li>-E: Cost to extend a gap. Default = 2 (nuc-nuc) / 1 (protein) /
	 * non-affine for megablast</li>
	 * <li>-r: integer to reward for match. Default = 1</li>
	 * <li>-q: negative integer for penalty to allow mismatch. Default = -3</li>
	 * <li>-e: expectation value. Default = 10.0</li>
	 * <li>-W: word size. Default = 3 (proteins) / 11 (nuc-nuc) / 28 (megablast)
	 * </li>
	 * <li>-y: dropoff for blast extensions in bits, using default if not
	 * specified. Default = 20 for blastn, 7 for all others (except megablast
	 * for which it is not applicable).</li>
	 * <li>-X: X dropoff value for gapped alignment, in bits. Default = 30 for
	 * blastn/megablast, 15 for all others.</li>
	 * <li>-Z: final X dropoff value for gapped alignement, in bits. Default =
	 * 50 for blastn, 25 for all others (except megablast for which it is not
	 * applicable)</li>
	 * <li>-P: equals 0 for multiple hits 1-pass, 1 for single hit 1-pass. Does
	 * not apply to blastn ou megablast.</li>
	 * <li>-A: multiple hits window size. Default = 0 (for single hit algorithm)
	 * </li>
	 * <li>-I: number of database sequences to save hits for. Default = 500</li>
	 * <li>-Y: effective length of the search space. Default = 0 (0 represents
	 * using the whole space)</li>
	 * <li>-z: a real specifying the effective length of the database to use.
	 * Default = 0 (0 represents the real size)</li>
	 * <li>-c: an integer representing pseudocount constant for PSI-BLAST.
	 * Default = 7</li>
	 * <li>-F: any filtering directive</li>
	 * </ul>
	 * 
	 * <p>WARNING!! This method is still very much in flux and might not work as expected...</p>
	 * <p>
	 * You have to be aware that at not moment is there any error checking on
	 * the use of these parameters by this class.
	 * </p>
	 * 
	 * @param aStr
	 *            : a String with any number of optional parameters with an
	 *            associated value.
	 * 
	 */
	public void setAdvancedOptions(String aStr) {
		this.param.put("OTHER_ADVANCED","OTHER_ADVANCED="+ aStr);
	}

	/**
	 * 
	 * Simply return the string given as argument via setBlastAdvancedOptions
	 * 
	 * @return advanced :the string with the advanced options
	 */
	public String getBlastAdvancedOptions() {
		return this.param.get("OTHER_ADVANCED");
	}
	public String getAlignmentOption(String key) throws BioException {
		if(param.containsKey(key)){
			return this.param.get(key);}
		else{
			throw new BioException("The key named "+key+" is not set in this RemoteQBlastOutputProperties object");
		}
	}

	public void setAlignementOption(String key, String val) {
		this.param.put(key, val);
	}

	public Set<String> getAlignmentOptions() {
		return param.keySet();
	}
}

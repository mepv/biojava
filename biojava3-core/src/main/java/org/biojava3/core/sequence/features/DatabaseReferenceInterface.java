/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.biojava3.core.sequence.features;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author Scooter Willis <willishf at gmail dot com>
 */
public interface DatabaseReferenceInterface {

    public LinkedHashMap<String,ArrayList<DBReferenceInfo>> getDatabaseReferences() throws Exception;
}

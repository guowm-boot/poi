/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.poifs.filesystem;

import static org.apache.poi.POITestCase.assertContains;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.POIDataSamples;
import org.apache.poi.hssf.HSSFTestDataSamples;
import org.apache.poi.hssf.OldExcelFormatException;
import org.junit.Test;

/**
 * Class to test that POIFS complains when given older non-OLE2
 *  formats. See also {@link TestOfficeXMLException} for OOXML
 *  checks
 */
public class TestNotOLE2Exception {
	private static InputStream openXLSSampleStream(String sampleFileName) {
		return HSSFTestDataSamples.openSampleFileStream(sampleFileName);
	}
    private static InputStream openDOCSampleStream(String sampleFileName) {
        return POIDataSamples.getDocumentInstance().openResourceAsStream(sampleFileName);
    }

    @Test
	public void testRawXMLException() throws IOException {
        try (InputStream in = openXLSSampleStream("SampleSS.xml")) {
            new POIFSFileSystem(in).close();
            fail("expected exception was not thrown");
        } catch(NotOLE2FileException e) {
            // expected during successful test
            assertContains(e.getMessage(), "The supplied data appears to be a raw XML file");
            assertContains(e.getMessage(), "Formats such as Office 2003 XML");
        }
    }

    @Test
    public void testMSWriteException() throws IOException {
        try (InputStream in = openDOCSampleStream("MSWriteOld.wri")) {
            new POIFSFileSystem(in).close();
            fail("expected exception was not thrown");
        } catch(NotOLE2FileException e) {
            // expected during successful test
            assertContains(e.getMessage(), "The supplied data appears to be in the old MS Write");
            assertContains(e.getMessage(), "doesn't currently support");
        }
    }

    @Test
	public void testBiff3Exception() throws IOException {
        try (InputStream in = openXLSSampleStream("testEXCEL_3.xls")) {
            new POIFSFileSystem(in).close();
            fail("expected exception was not thrown");
        } catch(OldExcelFormatException e) {
            // expected during successful test
            assertContains(e.getMessage(), "The supplied data appears to be in BIFF3 format");
            assertContains(e.getMessage(), "try OldExcelExtractor");
        }
	}

    @Test
    public void testBiff4Exception() throws IOException {
        try (InputStream in = openXLSSampleStream("testEXCEL_4.xls")) {
            new POIFSFileSystem(in).close();
            fail("expected exception was not thrown");
        } catch(OldExcelFormatException e) {
            // expected during successful test
            assertContains(e.getMessage(), "The supplied data appears to be in BIFF4 format");
            assertContains(e.getMessage(), "try OldExcelExtractor");
        }
    }
}

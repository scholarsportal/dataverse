/*
 * Copyright 2018 Forschungszentrum Jülich GmbH
 * SPDX-License-Identifier: Apache 2.0
 */
package edu.harvard.iq.dataverse.dataaccess;

import edu.harvard.iq.dataverse.DataFile;
import edu.harvard.iq.dataverse.Dataset;
import edu.harvard.iq.dataverse.GlobalId;
import edu.harvard.iq.dataverse.mocks.MocksFactory;
import edu.harvard.iq.dataverse.pidproviders.doi.AbstractDOIProvider;
import edu.harvard.iq.dataverse.util.UrlSignerUtil;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.io.IOException;
import java.nio.file.Paths;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class RemoteOverlayAccessIOTest {

    @Mock

    private Dataset dataset;
    private DataFile datafile;
    private DataFile badDatafile;
    private String baseStoreId="182ad2bda2f-c3508e719076";
    private String logoPath = "images/dataverse_project_logo.svg";
    private String authority = "10.5072";
    private String identifier = "F2/ABCDEF";

    @BeforeEach
    public void setUp() {
        System.setProperty("dataverse.files.test.type", "remote");
        System.setProperty("dataverse.files.test.label", "testOverlay");
        System.setProperty("dataverse.files.test.base-url", "https://data.qdr.syr.edu/resources");
        System.setProperty("dataverse.files.test.base-store", "file");
        System.setProperty("dataverse.files.test.download-redirect", "true");
        System.setProperty("dataverse.files.test.remote-store-name", "DemoDataCorp");
        System.setProperty("dataverse.files.test.secret-key", "12345"); // Real keys should be much longer, more random
        System.setProperty("dataverse.files.file.type", "file");
        System.setProperty("dataverse.files.file.label", "default");
        datafile = MocksFactory.makeDataFile();
        dataset = MocksFactory.makeDataset();
        dataset.setGlobalId(new GlobalId(AbstractDOIProvider.DOI_PROTOCOL, authority, identifier, "/", AbstractDOIProvider.DOI_RESOLVER_URL, null));
        datafile.setOwner(dataset);
        datafile.setStorageIdentifier("test://" + baseStoreId + "//" + logoPath);

        badDatafile = MocksFactory.makeDataFile();
        badDatafile.setOwner(dataset);
        badDatafile.setStorageIdentifier("test://" + baseStoreId + "//../.." + logoPath);
    }

    @AfterEach
    public void tearDown() {
        System.clearProperty("dataverse.files.test.type");
        System.clearProperty("dataverse.files.test.label");
        System.clearProperty("dataverse.files.test.base-url");
        System.clearProperty("dataverse.files.test.base-store");
        System.clearProperty("dataverse.files.test.download-redirect");
        System.clearProperty("dataverse.files.test.label");
        System.clearProperty("dataverse.files.test.remote-store-name");
        System.clearProperty("dataverse.files.test.secret-key");
        System.clearProperty("dataverse.files.file.type");
        System.clearProperty("dataverse.files.file.label");
    }



    @Test
    void testRemoteOverlayIdentifierFormats() throws IOException {
        
        assertTrue(DataAccess.isValidDirectStorageIdentifier(datafile.getStorageIdentifier()));
        assertFalse(DataAccess.isValidDirectStorageIdentifier(badDatafile.getStorageIdentifier()));
        assertFalse(DataAccess.isValidDirectStorageIdentifier(datafile.getStorageIdentifier().replace("test", "bad")));
    }

}

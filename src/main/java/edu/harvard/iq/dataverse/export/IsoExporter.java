
package edu.harvard.iq.dataverse.export;

import com.google.auto.service.AutoService;
import com.google.gson.Gson;
import edu.harvard.iq.dataverse.ControlledVocabularyValue;
import edu.harvard.iq.dataverse.DatasetFieldConstant;
import edu.harvard.iq.dataverse.api.dto.FieldDTO;
import edu.harvard.iq.dataverse.api.dto.MetadataBlockDTO;

import edu.harvard.iq.dataverse.api.dto.DatasetDTO;
import edu.harvard.iq.dataverse.api.dto.DatasetVersionDTO;
import edu.harvard.iq.dataverse.api.dto.FileDTO;
import edu.harvard.iq.dataverse.api.dto.LicenseDTO;

import edu.harvard.iq.dataverse.export.ddi.DdiExportUtil;
import edu.harvard.iq.dataverse.util.xml.XmlWriterUtil;
import io.gdcc.spi.export.ExportDataProvider;
import io.gdcc.spi.export.ExportException;
import io.gdcc.spi.export.Exporter;
import io.gdcc.spi.export.XMLExporter;
import edu.harvard.iq.dataverse.util.BundleUtil;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;

import jakarta.json.JsonObject;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.solr.common.util.IOUtils;

import javax.xml.stream.XMLOutputFactory;

/**
 * This exporter is for the "full" DDI, that includes the file-level, <data> and
 * <var> metadata.
 *
 * @author Leonid Andreev (based on the original DDIExporter by
 * @author skraffmi - renamed OAI_DDIExporter)
 */
@AutoService(Exporter.class)
public class IsoExporter implements XMLExporter {
    private static final Logger logger = Logger.getLogger(IsoExporter.class.getCanonicalName());
    public static String DEFAULT_XML_NAMESPACE = "";
    public static String DEFAULT_XML_SCHEMALOCATION = "";
    public static String DEFAULT_XML_VERSION = "";
    public static final String PROVIDER_NAME = "iso-3";

    @Override
    public String getFormatName() {
        return PROVIDER_NAME;
    }

    @Override
    public String getDisplayName(Locale locale) {
        String displayName = BundleUtil.getStringFromBundle("dataset.exportBtn.itemLabel.iso-3", locale);
        return Optional.ofNullable(displayName).orElse("ISO-3");
    }

    @Override
    public void exportDataset(ExportDataProvider dataProvider, OutputStream outputStream) throws ExportException {
        XMLStreamWriter xmlw = null;
        //XMLStreamWriter is not auto-closable - can't use try-with-resources here
        try {
            xmlw = XMLOutputFactory.newInstance().createXMLStreamWriter(outputStream);
            xmlw.writeStartDocument();
            xmlw.flush();

            Gson gson = new Gson();
            DatasetDTO datasetDto = gson.fromJson(dataProvider.getDatasetJson().toString(), DatasetDTO.class);


            DatasetVersionDTO datasetVersionDTO = datasetDto.getDatasetVersion();
            FieldDTO geographicBoundingBoxDTO = null;
            FieldDTO otherIdDTO = null;
            FieldDTO languageDTO = null;
            FieldDTO keywordDTO = null;
            FieldDTO referenceSystemInfoDTO = null;
            FieldDTO authorDTO = null;
            FieldDTO titleDTO = null;
            FieldDTO alternativeTitleDTO = null;
            FieldDTO distributionDateDTO = null;
            FieldDTO distributionDTO = null;
            FieldDTO geoReferenceDateDTO = null;
            FieldDTO topicClassificationDTO = null;
            FieldDTO noteDTO = null;
            FieldDTO seriesDTO = null;
            FieldDTO softwareDTO = null;
            FieldDTO lineageStatementDTO  = null;
            FieldDTO sourceDescriptionDTO  = null;
            FieldDTO processStepDTO  = null;
            FieldDTO spatialResolutionDTO  = null;
            FieldDTO spatialRepresentationTypeDTO = null;
            FieldDTO geometricObjectCountDTO  = null;
            FieldDTO geometricObjectTypeCodeDTO  = null;
            FieldDTO datasetContactDTO  = null;
            FieldDTO descriptionDTO  = null;
            FieldDTO geoResourceTypeDTO  = null;

            for (Map.Entry<String, MetadataBlockDTO> entry : datasetVersionDTO.getMetadataBlocks().entrySet()) {
                String key = entry.getKey();
                MetadataBlockDTO value = entry.getValue();

                if ("citation".equals(key)) {
                    for (FieldDTO fieldDTO : value.getFields()) {
                        logger.info(fieldDTO.getTypeName());
                        if (DatasetFieldConstant.otherId.equals(fieldDTO.getTypeName())) {
                            otherIdDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.language.equals(fieldDTO.getTypeName())) {
                            logger.info("This is language");
                            languageDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.keyword.equals(fieldDTO.getTypeName())) {
                            logger.info("This is keyword");
                            keywordDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.author.equals(fieldDTO.getTypeName())) {
                            logger.info("This is author");
                            authorDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.title.equals(fieldDTO.getTypeName())) {
                            titleDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.alternativeTitle.equals(fieldDTO.getTypeName())) {
                            alternativeTitleDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.distributionDate.equals(fieldDTO.getTypeName())) {
                            distributionDateDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.topicClassification.equals(fieldDTO.getTypeName())) {

                            topicClassificationDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.notesText.equals(fieldDTO.getTypeName())) {
                            noteDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.series.equals(fieldDTO.getTypeName())) {
                            seriesDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.software.equals(fieldDTO.getTypeName())) {
                            softwareDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.datasetContact.equals(fieldDTO.getTypeName())) {
                            datasetContactDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.description.equals(fieldDTO.getTypeName())) {
                            descriptionDTO = fieldDTO;
                        }


                    }
                }

                if ("geospatial".equals(key)) {
                    for (FieldDTO fieldDTO : value.getFields()) {

                        if (DatasetFieldConstant.geographicBoundingBox.equals(fieldDTO.getTypeName())) {
                            geographicBoundingBoxDTO = fieldDTO;
                        }

                        if (DatasetFieldConstant.referenceSystemInfo.equals(fieldDTO.getTypeName())) {
                            logger.info("This is reference system info");
                            referenceSystemInfoDTO = fieldDTO;
                        }

                        if (DatasetFieldConstant.distribution.equals(fieldDTO.getTypeName())) {
                            distributionDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.geoReferenceDate.equals(fieldDTO.getTypeName())) {
                            geoReferenceDateDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.lineageStatement.equals(fieldDTO.getTypeName())) {
                            lineageStatementDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.sourceDescription.equals(fieldDTO.getTypeName())) {
                             sourceDescriptionDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.processStep.equals(fieldDTO.getTypeName())) {
                            processStepDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.spatialResolution.equals(fieldDTO.getTypeName())) {
                            spatialResolutionDTO = fieldDTO;
                        }

                        if (DatasetFieldConstant.spatialRepresentationType.equals(fieldDTO.getTypeName())) {
                            spatialRepresentationTypeDTO = fieldDTO;
                        }

                        if (DatasetFieldConstant.geometricObjectCount.equals(fieldDTO.getTypeName())) {
                            geometricObjectCountDTO = fieldDTO;
                        }
                        if (DatasetFieldConstant.geoResourceType.equals(fieldDTO.getTypeName())) {
                            geoResourceTypeDTO = fieldDTO;
                        }

                    }
                }
            }
            xmlw.writeStartElement("mdb:MD_Metadata");
            writeNamespaces(xmlw);
            writeDatasetPersistentId(xmlw, datasetDto.getIdentifier(), datasetDto.getAuthority(), datasetDto.getProtocol());
            writeDefaultLocale(xmlw, languageDTO);
            writeDateInfo(xmlw, descriptionDTO);
            writeAlternativeMetadataReference(xmlw,otherIdDTO);
            writeIdentificationInfo(xmlw, geographicBoundingBoxDTO, keywordDTO,
                    authorDTO, titleDTO, alternativeTitleDTO, distributionDateDTO, geoReferenceDateDTO,
                    topicClassificationDTO, noteDTO, seriesDTO, softwareDTO, spatialResolutionDTO,
                    spatialRepresentationTypeDTO, datasetVersionDTO.getTermsOfUse(), datasetContactDTO, descriptionDTO);
            writeReferenceSystemInfo(xmlw, referenceSystemInfoDTO);
            writeDistributionInfo(xmlw, distributionDTO);
            writeSpatialRepresentationInfo(xmlw, geometricObjectCountDTO, geometricObjectTypeCodeDTO);
            writeResourceLineage(xmlw, lineageStatementDTO, sourceDescriptionDTO, processStepDTO ); //unclear
            writeMetadataMaintenance(xmlw, datasetVersionDTO.getOriginalArchive());
            // writeMetadataScope(xmlw, geoResourceTypeDTO); //not clear


            xmlw.writeEndElement(); // MD_Metadata
            xmlw.flush();
        } catch (XMLStreamException xse) {
            throw new ExportException("Caught XMLStreamException performing ISO-3 export", xse);
        } finally {
            if (xmlw != null) {
                try {
                    xmlw.close();
                } catch (XMLStreamException e) {
                    // Log this exception, but don't rethrow as it's not the primary issue
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeMetadataScope(XMLStreamWriter xmlw, FieldDTO geoResourceTypeDTO) throws XMLStreamException {
        //mdb:MD_Metadata/mdb:metadataScope/mdb:MD_MetadataScope
        String geoResourceType = geoResourceTypeDTO.getSinglePrimitive();
        if (!geoResourceType.isEmpty()) {
            xmlw.writeStartElement("mdb:metadataScope");
            xmlw.writeStartElement("mdb:MD_MetadataScope");
            xmlw.writeCharacters(geoResourceType);
            xmlw.writeEndElement(); //
            xmlw.writeEndElement();
        }
    }

    private void writeDateInfo(XMLStreamWriter xmlw, FieldDTO descriptionDTO) throws XMLStreamException {
        for (HashSet<FieldDTO> foo : descriptionDTO.getMultipleCompound()) {
            String date = "";
            for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                FieldDTO next = iterator.next();
                if (DatasetFieldConstant.descriptionDate.equals(next.getTypeName())) {
                    date = next.getSinglePrimitive();
                }
            }
            if (!date.isEmpty()) {
                //mdb:dateInfo/cit:CI_Date/cit:date/gco:DateTime
                xmlw.writeStartElement("mdb:dateInfo");
                dateISO(xmlw, date, "publication"); //For description (abstract) date code is not clear
                xmlw.writeEndElement(); //mdb:dateInfo
            }
        }
    }

    private void writeMetadataMaintenance(XMLStreamWriter xmlw, String originalArchive) throws XMLStreamException {
        //mdb:metadataMaintenance/mmi:MD_MaintenanceInformation/mmi:maintenanceNote/gco:CharacterString
        if (!originalArchive.isEmpty()) {
            xmlw.writeStartElement("mdb:metadataMaintenance");
            xmlw.writeStartElement("mmi:MD_MaintenanceInformation");
            xmlw.writeStartElement("mmi:maintenanceNote");
            xmlw.writeStartElement("gco:CharacterString");
            xmlw.writeCharacters(originalArchive);
            xmlw.writeEndElement(); //gco:CharacterString
            xmlw.writeEndElement(); //mmi:maintenanceNote
            xmlw.writeEndElement(); //mmi:MD_MaintenanceInformation
            xmlw.writeEndElement(); //mdb:metadataMaintenance
        }
    }

    private void writeSpatialRepresentationInfo(XMLStreamWriter xmlw, FieldDTO geometricObjectCountDTO, FieldDTO geometricObjectTypeCodeDTO) throws XMLStreamException {
        String geometricObjectCount = "";
        if (geometricObjectCountDTO != null) {
            geometricObjectCount = geometricObjectCountDTO.getSinglePrimitive();
        }
        String geometricObjectTypeCode = "";
        if (geometricObjectTypeCodeDTO !=null) {
            geometricObjectTypeCode = geometricObjectTypeCodeDTO.getSinglePrimitive();
        }

        if (!geometricObjectCount.isEmpty() || !geometricObjectTypeCode.isEmpty()) {
            xmlw.writeStartElement("mdb:SpatialRepresentationInfo");
            xmlw.writeStartElement("msr:MD_SpatialRepresentation");
            xmlw.writeStartElement("msr:MD_VectorSpatialRepresentation");
            xmlw.writeStartElement("msr:geometricObjects");
            xmlw.writeStartElement("msr:MD_GeometricObjects");
            if (!geometricObjectCount.isEmpty()) {
                xmlw.writeStartElement("msr:geometricObjectCount");
                xmlw.writeStartElement("gco:Integer");
                xmlw.writeCharacters(geometricObjectCount);
                xmlw.writeEndElement(); //gco:Integer
                xmlw.writeEndElement(); //mrs:geometricObjectCount
            }
            if (!geometricObjectTypeCode.isEmpty()) {
                xmlw.writeStartElement("msr:geometricObjectType");
                xmlw.writeStartElement("msr:MD_GeometricObjectTypeCode");
                xmlw.writeAttribute("codeSpace","ISOTC211/19115");
                xmlw.writeAttribute("codeList","http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_GeometricObjectTypeCode");
                xmlw.writeAttribute("codeListValue",geometricObjectTypeCode);
                xmlw.writeCharacters(geometricObjectTypeCode);
                xmlw.writeEndElement(); //msr:MD_GeometricObjectTypeCode
                xmlw.writeEndElement(); //msr:geometricObjectType
            }
            xmlw.writeEndElement(); //msr:MD_GeometricObjects
            xmlw.writeEndElement(); //msr:geometricObjects
            xmlw.writeEndElement(); //msr:MD_VectorSpatialRepresentation
            xmlw.writeEndElement(); //msr:MD_SpatialRepresentation
            xmlw.writeEndElement(); //mdb:SpatialRepresentationInfo
        }
        //        <mdb:spatialRepresentationInfo>
//                <msr:MD_SpatialRepresentation>
//                <msr:MD_GridSpatialRepresentation>
//                <msr:numberOfDimensions>
//                <gco:Integer>100</gco:Integer>
//                </msr:numberOfDimensions>
//                <msr:axisDimensionProperties>
//                <msr:numberOfDimensions>
//                <msr:MD_CellGeometryCode codeSpace="ISOTC211/19115" codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_DimensionNameTypeCode" codeListValue="row">row</msr:MD_CellGeometryCode>
//                </msr:numberOfDimensions>
//                <msr:axisDimensionProperties>
//                <gco:Integer>12</gco:Integer>
//                </msr:axisDimensionProperties>
//                <msr:cellGeometry>1222</msr:cellGeometry>
//                <msr:transformationParameterAvailability xsi:type="lan:PT_FreeText_PropertyType">
//                <gco:CharacterString>Dimension Title</gco:CharacterString>
//                </msr:transformationParameterAvailability>
//                <msr:transformationParameterAvailability xsi:type="lan:PT_FreeText_PropertyType">
//                <gco:CharacterString>Dimension Title</gco:CharacterString>
//                </msr:transformationParameterAvailability>
//                </msr:axisDimensionProperties>
//                <msr:cellGeometry>
//                <msr:MD_DimensionNameTypeCode codeSpace="ISOTC211/19115" codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_DimensionNameTypeCode" codeListValue="point">point</msr:MD_DimensionNameTypeCode>
//                </msr:cellGeometry>
//                <msr:transformationParameterAvailability>
//                <gco:Boolean>true</gco:Boolean>
//                </msr:transformationParameterAvailability>
//                </msr:MD_GridSpatialRepresentation>
//                <msr:MD_Georectified>
//                <msr:checkPointAvailability>
//                <gco:Boolean>true</gco:Boolean>
//                </msr:checkPointAvailability>
//                <msr:checkPointDescription xsi:type="lan:PT_FreeText_PropertyType">
//                <gco:CharacterString>Check Point Description</gco:CharacterString>
//                </msr:checkPointDescription>
//                <msr:cornerPoints>Corner Points</msr:cornerPoints>
//                <msr:centrePoint>Centre Point</msr:centrePoint>
//                <msr:pointInPixel>Point In Pixel</msr:pointInPixel>
//                <msr:transformationDimensionDescription xsi:type="lan:PT_FreeText_PropertyType">
//                <gco:CharacterString>Transformation Dimension Description</gco:CharacterString>
//                </msr:transformationDimensionDescription>
//                <msr:transformationDimensionMapping xsi:type="lan:PT_FreeText_PropertyType">
//                <gco:CharacterString>Transformation Dimension Mapping</gco:CharacterString>
//                </msr:transformationDimensionMapping>
//                </msr:MD_Georectified>
//                <msr:MD_Georeferenceable>
//                <msr:numberOfDimensions>
//                <gco:Boolean>true</gco:Boolean>
//                </msr:numberOfDimensions>
//                <msr:axisDimensionProperties>
//                <gco:Boolean>true</gco:Boolean>
//                </msr:axisDimensionProperties>
//                <msr:cellGeometry xsi:type="lan:PT_FreeText_PropertyType">
//                <gco:CharacterString>Orientation Parameter Description</gco:CharacterString>
//                </msr:cellGeometry>
//                <msr:transformationParameterAvailability>Georeference Parameters</msr:transformationParameterAvailability>
//                <msr:transformationParameterAvailability>
//                <cit:CI_Citation>
//                <cit:title xsi:type="lan:PT_FreeText_PropertyType"/>
//                </cit:CI_Citation>
//                </msr:transformationParameterAvailability>
//                </msr:MD_Georeferenceable>
//                <msr:MD_VectorSpatialRepresentation>
//                <msr:numberOfDimensions>
//                <msr:MD_TopologyLevelCode codeSpace="ISOTC211/19115" codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_TopologyLevelCode" codeListValue="geometryOnly">geometryOnly</msr:MD_TopologyLevelCode>
//                </msr:numberOfDimensions>
//                <msr:axisDimensionProperties>
//                <msr:numberOfDimensions>
//                <msr:MD_GeometryObjectTypeCode codeSpace="ISOTC211/19115" codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_GeometryObjectTypeCode" codeListValue="point">point</msr:MD_GeometryObjectTypeCode>
//                </msr:numberOfDimensions>
//                <msr:axisDimensionProperties>
//                <gco:Integer>1200</gco:Integer>
//                </msr:axisDimensionProperties>
//                </msr:axisDimensionProperties>
//                </msr:MD_VectorSpatialRepresentation>
//                <msr:MD_Dimension>
//                <msr:numberOfDimensions>
//                <msr:MD_CellGeometryCode codeSpace="ISOTC211/19115" codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_DimensionNameTypeCode" codeListValue="crossTrack">crossTrack</msr:MD_CellGeometryCode>
//                </msr:numberOfDimensions>
//                <msr:axisDimensionProperties>
//                <gco:Integer>1</gco:Integer>
//                </msr:axisDimensionProperties>
//                <msr:cellGeometry>Resolution</msr:cellGeometry>
//                <msr:transformationParameterAvailability xsi:type="lan:PT_FreeText_PropertyType">
//                <gco:CharacterString>Dimension Title</gco:CharacterString>
//                </msr:transformationParameterAvailability>
//                <msr:transformationParameterAvailability xsi:type="lan:PT_FreeText_PropertyType">
//                <gco:CharacterString>Dimension Title</gco:CharacterString>
//                </msr:transformationParameterAvailability>
//                </msr:MD_Dimension>
//                </msr:MD_SpatialRepresentation>
    }

    private void writeResourceLineage(XMLStreamWriter xmlw, FieldDTO lineageStatementDTO,
                                      FieldDTO sourceDescriptionDTO, FieldDTO processStepDTO) throws XMLStreamException {
        if (lineageStatementDTO != null || sourceDescriptionDTO != null || processStepDTO != null) {
            xmlw.writeStartElement("mdb:resourceLineage");
            xmlw.writeStartElement("mrl:LI_Lineage");
            if (lineageStatementDTO != null) {
                String lineageStatement = lineageStatementDTO.getTypeName();
                if (!lineageStatement.isEmpty()) {
                    xmlw.writeStartElement("mrl:statement");
                    xmlw.writeStartElement("gco:CharacterString");
                    xmlw.writeCharacters(lineageStatement);
                    xmlw.writeEndElement(); //gco:CharacterString
                    xmlw.writeEndElement(); //mrl:statement
                }
            }
            if (sourceDescriptionDTO != null) {
                for (String source : sourceDescriptionDTO.getMultiplePrimitive()) {
                    xmlw.writeStartElement("mrl:source");
                    xmlw.writeStartElement("mrl:LI_Source");
                    xmlw.writeStartElement("mrl:description");
                    xmlw.writeStartElement("gco:CharacterString");
                    xmlw.writeCharacters(source);
                    xmlw.writeEndElement(); //gco:CharacterString
                    xmlw.writeEndElement(); //mrl:description
                    xmlw.writeEndElement(); //mrl:LI_Source
                    xmlw.writeEndElement(); //mrl:source
                }
            }
            if (processStepDTO != null) {
                for (String process : processStepDTO.getMultiplePrimitive()) {
                    xmlw.writeStartElement("mrl:processStep");
                    xmlw.writeStartElement("mrl:LI_ProcessStep");
                    xmlw.writeStartElement("mrl:description");
                    xmlw.writeStartElement("gco:CharacterString");
                    xmlw.writeCharacters(process);
                    xmlw.writeEndElement(); //gco:CharacterString
                    xmlw.writeEndElement(); //mrl:description
                    xmlw.writeEndElement(); //mrl:LI_ProcessStep
                    xmlw.writeEndElement(); //mrl:processStep

                }
            }
            xmlw.writeEndElement(); //mdb:resourceLineage
            xmlw.writeEndElement(); //mrl:LI_Lineage
        }
    }
    private void writeDatasetPersistentId(XMLStreamWriter xmlw, String persistentId, String authority, String protocol) throws XMLStreamException {
        xmlw.writeStartElement("mdb:metadataIdentifier");
        xmlw.writeStartElement("mcc:MD_Identifier");
        xmlw.writeStartElement("authority");
        xmlw.writeStartElement("cit:CI_Citation");
        xmlw.writeStartElement("cit:title");
        xmlw.writeStartElement("gco:CharacterString");
        xmlw.writeCharacters(protocol);
        xmlw.writeEndElement(); //gco:CharacterString
        xmlw.writeEndElement(); //cit:title
        xmlw.writeStartElement("cit:presentationForm");
        xmlw.writeStartElement("cit:CI_PresentationFormCode");
        xmlw.writeAttribute("codeList" ,"http://standards.iso.org/iso/19115/resources/Codelist/cat/codeLists.xml#CI_PresentationFormCode");
        xmlw.writeAttribute("codeListValue","multimediaHardcopy");
        xmlw.writeCharacters("multimediaHardcopy");
        xmlw.writeEndElement(); //cit:CI_PresentationFormCode
        xmlw.writeEndElement(); //cit:presentationForm
        xmlw.writeEndElement(); //cit:CI_Citation
        xmlw.writeEndElement(); //mcc:authority
        xmlw.writeStartElement("mcc:code");
        xmlw.writeStartElement("gco:CharacterString");
        xmlw.writeCharacters(protocol+ ":"+ authority + "/" + persistentId);
        xmlw.writeEndElement(); //gco:CharacterString
        xmlw.writeEndElement(); //mcc:code
        xmlw.writeEndElement(); //mcc:MD_Identifier
        xmlw.writeEndElement(); //mdb:metadataIdentifier

    }

    private void writeNamespaces(XMLStreamWriter xmlw) throws XMLStreamException {
        xmlw.writeAttribute("xmlns:cat", "http://standards.iso.org/iso/19115/-3/cat/1.0");
        xmlw.writeAttribute("xmlns:cit","http://standards.iso.org/iso/19115/-3/cit/2.0");
        xmlw.writeAttribute("xmlns:dc","http://purl.org/dc/terms/");
        xmlw.writeAttribute("xmlns:gcx", "http://standards.iso.org/iso/19115/-3/gcx/1.0");
        xmlw.writeAttribute("xmlns:gex", "http://standards.iso.org/iso/19115/-3/gex/1.0");
        xmlw.writeAttribute("xmlns:lan", "http://standards.iso.org/iso/19115/-3/lan/1.0");
        xmlw.writeAttribute("xmlns:srv", "http://standards.iso.org/iso/19115/-3/srv/2.0");
        xmlw.writeAttribute("xmlns:mac", "http://standards.iso.org/iso/19115/-3/mac/2.0");
        xmlw.writeAttribute("xmlns:mas", "http://standards.iso.org/iso/19115/-3/mas/1.0");
        xmlw.writeAttribute("xmlns:mcc", "http://standards.iso.org/iso/19115/-3/mcc/1.0");
        xmlw.writeAttribute("xmlns:mco", "http://standards.iso.org/iso/19115/-3/mco/1.0" );
        xmlw.writeAttribute("xmlns:mda", "http://standards.iso.org/iso/19115/-3/mda/1.0");
        xmlw.writeAttribute("xmlns:mdb","http://standards.iso.org/iso/19115/-3/mdb/2.0");
        xmlw.writeAttribute("xmlns:mdt", "http://standards.iso.org/iso/19115/-3/mdt/1.0");
        xmlw.writeAttribute("xmlns:mex", "http://standards.iso.org/iso/19115/-3/mex/1.0");
        xmlw.writeAttribute("xmlns:mrl", "http://standards.iso.org/iso/19115/-3/mrl/1.0");
        xmlw.writeAttribute("xmlns:mds","http://standards.iso.org/iso/19115/-3/mds/1.0");
        xmlw.writeAttribute("xmlns:mmi", "http://standards.iso.org/iso/19115/-3/mmi/1.0");
        xmlw.writeAttribute("xmlns:mpc", "http://standards.iso.org/iso/19115/-3/mpc/1.0");
        xmlw.writeAttribute("xmlns:mrc", "http://standards.iso.org/iso/19115/-3/mrc/2.0");
        xmlw.writeAttribute("xmlns:mrd", "http://standards.iso.org/iso/19115/-3/mrd/1.0");
        xmlw.writeAttribute("xmlns:mri", "http://standards.iso.org/iso/19115/-3/mri/1.0");
        xmlw.writeAttribute("xmlns:mrs", "http://standards.iso.org/iso/19115/-3/mrs/1.0");
        xmlw.writeAttribute("xmlns:msr","http://standards.iso.org/iso/19115/-3/msr/2.0");
        xmlw.writeAttribute("xmlns:mdq", "http://standards.iso.org/iso/19157/-2/mdq/1.0" );
        xmlw.writeAttribute("xmlns:dqc", "http://standards.iso.org/iso/19157/-2/dqc/1.0");
        xmlw.writeAttribute("xmlns:gco", "http://standards.iso.org/iso/19115/-3/gco/1.0");
        xmlw.writeAttribute("xmlns:gfc", "http://standards.iso.org/iso/19110/gfc/1.1");
        xmlw.writeAttribute("xmlns:gml", "http://www.opengis.net/gml/3.2" );
        xmlw.writeAttribute("xmlns:xlink","http://www.w3.org/1999/xlink" );
        xmlw.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    }


    private void writeAlternativeMetadataReference(XMLStreamWriter xmlw, FieldDTO otherIdDTO) throws XMLStreamException {
        String otherId = "";
        String otherIdAgency = "";
        if (otherIdDTO != null) {
            for (HashSet<FieldDTO> foo : otherIdDTO.getMultipleCompound()) {
                for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                    FieldDTO next = iterator.next();
                    if (DatasetFieldConstant.otherIdValue.equals(next.getTypeName())) {
                        otherId = next.getSinglePrimitive();
                    }
                    if (DatasetFieldConstant.otherIdAgency.equals(next.getTypeName())) {
                        otherIdAgency = next.getSinglePrimitive();
                    }
                }
                if (!otherId.isEmpty()) {
                    xmlw.writeStartElement("mdb:alternativeMetadataReference");
                    xmlw.writeStartElement("cit:CI_Citation");
                    xmlw.writeStartElement("cit:title");
                    xmlw.writeAttribute("xsi:type", "lan:PT_FreeText_PropertyType");
                    xmlw.writeStartElement("gco:CharacterString");
                    xmlw.writeCharacters(otherIdAgency);
                    xmlw.writeEndElement(); //gco:CharacterString
                    xmlw.writeEndElement(); //cit:title
                    xmlw.writeStartElement("cit:identifier");
                    xmlw.writeStartElement("mcc:Identifier");
                    xmlw.writeStartElement("mcc:code");
                    xmlw.writeStartElement("gco:CharacterString");
                    xmlw.writeCharacters(otherId);
                    xmlw.writeEndElement(); //gco:CharacterString
                    xmlw.writeEndElement(); //mcc:code
//                xmlw.writeStartElement("mcc:codeSpace");
//                xmlw.writeStartElement("gco:CharacterString");
//                xmlw.writeCharacters(otherIdAgency);
//                xmlw.writeEndElement(); //gco:CharacterString
//                xmlw.writeEndElement(); //mcc:codeSpace
//                xmlw.writeStartElement("mcc:description");
//                xmlw.writeStartElement("gco:CharacterString");
//                xmlw.writeCharacters(otherIdAgency);
//                xmlw.writeEndElement(); //gco:CharacterString
//                xmlw.writeEndElement(); //mcc:description
                    xmlw.writeEndElement(); //mcc:MD_Identifier
                    xmlw.writeEndElement(); //cit:identifier
                    xmlw.writeStartElement("cit:presentationForm");
                    xmlw.writeStartElement("cit:CI_PresentationFormCode");
                    xmlw.writeAttribute("codeList", "http://standards.iso.org/iso/19115/resources/Codelist/cat/codeLists.xml#CI_PresentationFormCode");
                    xmlw.writeAttribute("codeListValue", "documentDigital");
                    xmlw.writeCharacters("documentDigital");
                    xmlw.writeEndElement(); //cit:CI_PresentationFormCode
                    xmlw.writeEndElement(); //cit:presentationForm
                    xmlw.writeEndElement(); //cit:CI_Citation
                    xmlw.writeEndElement(); //mdb:alternativeMetadataReference
                }
            }
        }
    }
    private void writeDefaultLocale(XMLStreamWriter xmlw, FieldDTO langDTO) throws XMLStreamException {

        logger.info("writeDefaultLocale");
        String language = "eng";
        if (langDTO != null) {
            for (String lang : langDTO.getMultipleVocab()) {

                if (lang.equals("English")) {
                    language = "eng";
                    break;
                } else if (lang.equals("French")) {
                    language = "fr";
                    break;
                }
            }
        }
//            if (!language.isEmpty() ) {
        xmlw.writeStartElement("mdb:defaultLocale");
        xmlw.writeStartElement("lan:PT_Locale");
        xmlw.writeStartElement("lan:language");
        xmlw.writeStartElement("lan:LanguageCode");
        xmlw.writeAttribute("codeList","http://www.loc.gov/standards/iso639-2/");
        xmlw.writeAttribute("codeListValue", "eng");
        xmlw.writeCharacters(language);
        xmlw.writeEndElement(); //LanguageCode
        xmlw.writeEndElement(); //language
        xmlw.writeStartElement("lan:country");
        xmlw.writeStartElement("lan:CountryCode");
        xmlw.writeAttribute("codeList","http://www.loc.gov/standards/iso366-1/");
        xmlw.writeAttribute("codeListValue", "can");
        xmlw.writeCharacters("can");
        xmlw.writeEndElement(); //CountryCode
        xmlw.writeEndElement(); //country
        xmlw.writeStartElement("lan:characterEncoding");
        xmlw.writeStartElement("lan:MD_CharacterSetCode");
        xmlw.writeAttribute("codeList","http://standards.iso.org/iso/19115/resources/Codelists/cat/codelists.xml#MD_CharacterSetCode");
        xmlw.writeAttribute("codeListValue", "utf8");
        xmlw.writeCharacters("utf8");
        xmlw.writeEndElement(); //MD_CharacterSetCode
        xmlw.writeEndElement(); //characterEncoding
        xmlw.writeEndElement(); //lan:PT_Locale
        xmlw.writeEndElement(); //mdb:defaultLocale

    }
    private void writeIdentificationInfo(XMLStreamWriter xmlw, FieldDTO geographicBoundingBoxDTO, FieldDTO keyword, FieldDTO authorDTO,
                                         FieldDTO titleDTO, FieldDTO alternativeTitleDTO, FieldDTO distributionDateDTO, FieldDTO geoReferenceDateDTO,
                                         FieldDTO topicClassDTO, FieldDTO noteDTO, FieldDTO seriesDTO, FieldDTO softwareDTO,
                                         FieldDTO spatialResolutionDTO, FieldDTO spatialRepresentationTypeDTO, String termsOfuse,
                                         FieldDTO datasetContactDTO, FieldDTO descriptionDTO) throws XMLStreamException {
        xmlw.writeStartElement("mdb:IdentificationInfo");
        xmlw.writeStartElement("mri:MD_DataIdentification");
        writeAbstractAndPurpose(xmlw, descriptionDTO);
        writeCitation(xmlw, authorDTO, titleDTO, alternativeTitleDTO, distributionDateDTO, geoReferenceDateDTO, seriesDTO);
        writePointOfContact(xmlw, datasetContactDTO);
        writeDescritiveKeywords(xmlw, keyword);
        writeExtent(xmlw, geographicBoundingBoxDTO);
        writeSpatialRepresentationType(xmlw, spatialRepresentationTypeDTO);
        writeSpatialResolution(xmlw,spatialResolutionDTO);
        writeTopicClass(xmlw, topicClassDTO);
        writeNote(xmlw, noteDTO);
        writeSoftware(xmlw, softwareDTO);
        writeResourceConstrains(xmlw, termsOfuse);
        xmlw.writeEndElement(); //IdentificationInfo
        xmlw.writeEndElement(); //mri:MD_DataIdentification
    }

    private void writeAbstractAndPurpose(XMLStreamWriter xmlw, FieldDTO descriptionDTO) throws XMLStreamException {
        if (descriptionDTO != null) {
            for (HashSet<FieldDTO> foo : descriptionDTO.getMultipleCompound()) {
                String description = "";
                String date = "";
                for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                    FieldDTO next = iterator.next();
                    if (DatasetFieldConstant.descriptionText.equals(next.getTypeName())) {
                        description = next.getSinglePrimitive();
                    }
                    if (DatasetFieldConstant.descriptionDate.equals(next.getTypeName())) {
                        date = next.getSinglePrimitive();
                    }
                }
                xmlw.writeStartElement("mri:abstract");
                xmlw.writeStartElement("gco:CharacterString");
                xmlw.writeCharacters(description);
                xmlw.writeEndElement(); //gco:CharacterString
                xmlw.writeEndElement(); //field
                xmlw.writeStartElement("mri:purpose"); //mri:abstract
                xmlw.writeStartElement("gco:CharacterString");
                xmlw.writeCharacters(description);
                xmlw.writeEndElement(); //gco:CharacterString
                xmlw.writeEndElement(); //mri:purpose
                //mri:abstract/gco:CharacterString
                //mri:purpose/gco:CharacterString
            }
        }
    }



    private void writePointOfContact(XMLStreamWriter xmlw, FieldDTO datasetContactDTO) throws XMLStreamException {
        //mri:pointOfContact/cit:CI_Responsibility/cit:party/cit:CI_Organisation/cit:individual/cit:CI_Individual/cit:name/gco:CharacterString
        for (HashSet<FieldDTO> foo : datasetContactDTO.getMultipleCompound()) {
            String datasetContactName = "";
            String datasetContactAffiliation = "";
            String datasetContactEmail = "";
            for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                FieldDTO next = iterator.next();
                if (DatasetFieldConstant.datasetContactName.equals(next.getTypeName())) {
                    datasetContactName = next.getSinglePrimitive();
                }
                if (DatasetFieldConstant.datasetContactAffiliation.equals(next.getTypeName())) {
                    //mdb:MD_Metadata/mdb:identificationInfo/mri:MD_DataIdentification/mri:pointOfContact/cit:CI_Responsibility/cit:party/cit:CI_Organisation/cit:name/gco:CharacterString
                    datasetContactAffiliation = next.getSinglePrimitive();
                }
                if (DatasetFieldConstant.datasetContactEmail.equals(next.getTypeName())) {
                    //mdb:MD_Metadata/mdb:identificationInfo/mri:MD_DataIdentification/mri:pointOfContact/cit:CI_Responsibility/cit:party/cit:CI_Organisation/cit:contactInfo/cit:CI_Contact/cit:address/cit:CI_Address/cit:electronicMailAddress/gco:CharacterString
                    datasetContactEmail = next.getSinglePrimitive();
                }
            }
            xmlw.writeStartElement("mri:pointOfContact");
            xmlw.writeStartElement("cit:CI_Responsibility");
            xmlw.writeStartElement("cit:party");
            xmlw.writeStartElement("cit:CI_Organisation");
            if (!datasetContactName.isEmpty()) {
                xmlw.writeStartElement("cit:individual");
                xmlw.writeStartElement("cit:CI_Individual");
                xmlw.writeStartElement("cit:name");
                xmlw.writeStartElement("gco:CharacterString");
                xmlw.writeCharacters(datasetContactName);
                xmlw.writeEndElement(); //gco:CharacterString
                xmlw.writeEndElement(); //cit:name
                xmlw.writeEndElement(); //cit:CI_Individual
                xmlw.writeEndElement(); //cit:individual
            }
            if (!datasetContactAffiliation.isEmpty()) {
                xmlw.writeStartElement("cit:name");
                xmlw.writeStartElement("gco:CharacterString");
                xmlw.writeCharacters(datasetContactAffiliation);
                xmlw.writeEndElement(); //gco:CharacterString
                xmlw.writeEndElement(); //cit:name
            }
            if (!datasetContactEmail.isEmpty()) {
                //cit:contactInfo/cit:CI_Contact/cit:address/cit:CI_Address/cit:electronicMailAddress/gco:CharacterString
                xmlw.writeStartElement("cit:contactInfo");
                xmlw.writeStartElement("cit:CI_Contact");
                xmlw.writeStartElement("cit:address");
                xmlw.writeStartElement("cit:CI_Address");
                xmlw.writeStartElement("cit:electronicMailAddress");
                xmlw.writeStartElement("gco:CharacterString");
                xmlw.writeCharacters(datasetContactEmail);
                xmlw.writeEndElement(); //gco:CharacterString
                xmlw.writeEndElement(); //cit:electronicMailAddress
                xmlw.writeEndElement(); //cit:CI_Address
                xmlw.writeEndElement(); //cit:address
                xmlw.writeEndElement(); //cit:CI_Contact
                xmlw.writeEndElement(); //cit:contactInfo
            }
            xmlw.writeEndElement(); //cit:CI_Organisation
            xmlw.writeEndElement(); //cit:party
            xmlw.writeEndElement(); //cit:CI_Responsibility
            xmlw.writeEndElement(); //mri:pointOfContact

        }

    }

    private void writeResourceConstrains(XMLStreamWriter xmlw, String termsOfUse ) throws XMLStreamException {
        if (!termsOfUse.isEmpty()) {
            xmlw.writeStartElement("mri:resourceConstraints");
            xmlw.writeStartElement("mco:MD_LegalConstraints");
            xmlw.writeStartElement("mco:useConstraints");
            xmlw.writeStartElement("mco:MD_RestrictionCode");
            xmlw.writeAttribute("codeList", "standards.iso.org/19115/-3/lan/1.0/codelists.xml#MD_RestrictionCode");
            xmlw.writeAttribute("codeListValue", termsOfUse);
            xmlw.writeCharacters(termsOfUse);
            xmlw.writeEndElement(); //mco:MD_RestrictionCode
            xmlw.writeEndElement(); //mco:useConstraints
            xmlw.writeEndElement(); //mco:MD_LegalConstraints
            xmlw.writeEndElement(); //mri:resourceConstraints
        }
        //        mri:resourceConstraints>
//                <mco:MD_LegalConstraints>
//                <mco:accessConstraints>
//                <mco:MD_RestrictionCode codeList="standards.iso.org/19115/-3/lan/1.0/codelists.xml#MD_RestrictionCode" codeListValue="license">license</mco:MD_RestrictionCode>
//                </mco:accessConstraints>
//                <mco:useConstraints>
//                <mco:MD_RestrictionCode codeList="standards.iso.org/19115/-3/lan/1.0/codelists.xml#MD_RestrictionCode" codeListValue="restricted">restricted</mco:MD_RestrictionCode>
//                </mco:useConstraints>
    }

    private void writeSpatialRepresentationType(XMLStreamWriter xmlw, FieldDTO spatialRepresentationTypeDTO ) throws XMLStreamException {
        if (spatialRepresentationTypeDTO != null) {
            String spatialRepresentationType = spatialRepresentationTypeDTO.getSinglePrimitive();
            if (!spatialRepresentationType.isEmpty()) {
                xmlw.writeStartElement("mri:spatialRepresentationType");
                xmlw.writeStartElement("mri:spatialRepresentationTypeCode");
                xmlw.writeAttribute("codeSpace", "ISOTC211/19115");
                xmlw.writeAttribute("codeList", "mcc:MD_SpatialRepresentationTypeCode");
                xmlw.writeAttribute("codeListValue", spatialRepresentationType);
                xmlw.writeCharacters(spatialRepresentationType);
                xmlw.writeEndElement(); //mri:spatialRepresentationTypeCode
                xmlw.writeEndElement(); //mri:spatialRepresentationType
            }
        }
    }

    private void writeSpatialResolution(XMLStreamWriter xmlw, FieldDTO spatialResolutionDTO) throws XMLStreamException {
        if (spatialResolutionDTO != null) {
            for (HashSet<FieldDTO> foo : spatialResolutionDTO.getMultipleCompound()) {
                String spatialResolutionValue = "";
                String spatialResolutionType = "";
                for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                    FieldDTO next = iterator.next();
                    if (DatasetFieldConstant.spatialResolutionValue.equals(next.getTypeName())) {
                        spatialResolutionValue = next.getSinglePrimitive();
                    }
                    if (DatasetFieldConstant.spatialResolutionType.equals(next.getTypeName())) {
                        spatialResolutionType = next.getSinglePrimitive();
                    }
                }
                if (!spatialResolutionValue.isEmpty()) {
                    xmlw.writeStartElement("mri:spatialResolution");
                    xmlw.writeStartElement("mri:MD_Resolution");
                    if (spatialResolutionType.equals("equivalentScale")) {
                        xmlw.writeStartElement("mri:equivalentScale");
                        xmlw.writeStartElement("mri:MD_RepresentativeFraction");
                        xmlw.writeStartElement("mri:denominator");
                        xmlw.writeStartElement("gco:Integer");
                        xmlw.writeCharacters(spatialResolutionValue);
                        xmlw.writeEndElement(); //gco:Integer
                        xmlw.writeEndElement(); //mri:denominator
                        xmlw.writeEndElement(); //mri:MD_RepresentativeFraction
                        xmlw.writeEndElement(); //mri:equivalentScale

                    }
                    xmlw.writeEndElement(); //mri:MD_Resolution
                    xmlw.writeEndElement(); //mri:spatialResolution
                }

            }
        }
    }

    private void writeSoftware(XMLStreamWriter xmlw, FieldDTO softwareDTO) throws XMLStreamException {
        int i = 0;
        String software = "";
        if (softwareDTO != null) {
            for (HashSet<FieldDTO> foo : softwareDTO.getMultipleCompound()) {
                String softwareName = "";
                String softwareVersion = "";

                for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                    FieldDTO next = iterator.next();
                    if (DatasetFieldConstant.softwareName.equals(next.getTypeName())) {
                        softwareName = next.getSinglePrimitive();
                        logger.info(softwareName);
                    }
                    if (DatasetFieldConstant.softwareVersion.equals(next.getTypeName())) {
                        softwareVersion = next.getSinglePrimitive();
                        logger.info(softwareVersion);
                    }
                }
                String software1 = softwareName + " Version: " + softwareVersion;
                if (i > 0) {
                    software = software + ";" + software1;
                } else {
                    software = software1;
                }
                i++;
            }
            if (!software.isEmpty()) {
                xmlw.writeStartElement("mri:environmentDescription"); //not repeatable
                xmlw.writeStartElement("gco:CharacterString");
                xmlw.writeCharacters(software);
                xmlw.writeEndElement(); //gco:CharacterString
                xmlw.writeEndElement(); //mri:environmentDescription
            }
        }
    }

    private void writeNote(XMLStreamWriter xmlw, FieldDTO noteDTO) throws XMLStreamException {
        if (noteDTO != null) {
            String note = noteDTO.getSinglePrimitive();
            xmlw.writeStartElement("mri:supplementalInformation");
            xmlw.writeAttribute("xsi:type","lan:PT_FreeText_PropertyType");
            xmlw.writeStartElement("gco:CharacterString");
            xmlw.writeCharacters(note);
            xmlw.writeEndElement(); //gco:CharacterString
            xmlw.writeEndElement(); //mri:supplementalInformation
        }
    }
    private void writeTopicClass(XMLStreamWriter xmlw, FieldDTO topicClassDTO) throws XMLStreamException {


        boolean isCVV = false;
        if (topicClassDTO != null) {
            for (HashSet<FieldDTO> foo : topicClassDTO.getMultipleCompound()) {

                String topicClassificationValue = "";
                String topicClassificationVocab = "";
                String topicClassificationURI = "";
                for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                    FieldDTO next = iterator.next();
                    if (DatasetFieldConstant.topicClassValue.equals(next.getTypeName())) {
                        // Currently getSingleVocab() is the same as getSinglePrimitive() so this works
                        // for either case
                        topicClassificationValue = next.getSinglePrimitive();
                        if (next.isControlledVocabularyField()) {
                            isCVV = true;
                        }
                    }
                    if (DatasetFieldConstant.topicClassVocab.equals(next.getTypeName())) {
                        topicClassificationVocab = next.getSinglePrimitive();
                    }
                    if (DatasetFieldConstant.topicClassVocabURI.equals(next.getTypeName())) {
                        topicClassificationURI = next.getSinglePrimitive();
                    }
                }
                if (!topicClassificationValue.isEmpty()) {
                    xmlw.writeStartElement("mri:topicCategory");
                    xmlw.writeStartElement("mri:MD_TopicCategoryCode");
                    xmlw.writeCharacters(topicClassificationValue);
                    xmlw.writeEndElement(); //mri:MD_TopicCategoryCode
                    xmlw.writeEndElement(); //mri:topicCategory
                }
            }
        }
    }
    private void writeCitation(XMLStreamWriter xmlw, FieldDTO authorDTO,FieldDTO titleDTO,
                               FieldDTO alternativeTitleDTO, FieldDTO distributionDateDTO,
                               FieldDTO geoReferenceDateDTO, FieldDTO seriesDTO ) throws XMLStreamException {
        xmlw.writeStartElement("mri:citation");
        xmlw.writeStartElement("cit:CI_Citation");
        title(xmlw, titleDTO);
        alternativeTitle(xmlw, alternativeTitleDTO);
        if (DatasetFieldConstant.author.equals(authorDTO.getTypeName())) {
            for (HashSet<FieldDTO> foo : authorDTO.getMultipleCompound()) {
                String authorName = "";

                for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                    FieldDTO next = iterator.next();
                    if (DatasetFieldConstant.authorName.equals(next.getTypeName())) {
                        authorName = next.getSinglePrimitive();
                    }

                }

                responsibleParty(xmlw, authorName, "author");
                responsibleParty(xmlw, authorName, "originator");

            }
        }
        if (distributionDateDTO != null) {
            String distributionDate = distributionDateDTO.getSinglePrimitive();
            if (!distributionDate.isEmpty()) {
                distributionDate(xmlw, distributionDate);
            }
        }
        if (geoReferenceDateDTO != null) {
            geoReferenceDate(xmlw, geoReferenceDateDTO);
        }
        if (seriesDTO != null) {
            writeSeries(xmlw, seriesDTO);
        }
        xmlw.writeEndElement(); //cit:CI_Citation
        xmlw.writeEndElement(); //mri:citation
    }

    private void writeSeries(XMLStreamWriter xmlw, FieldDTO seriesDTO) throws XMLStreamException {
        for (HashSet<FieldDTO> foo : seriesDTO.getMultipleCompound()) {
          String seriesName = "";
          String seriesInformation = "";
          for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
              FieldDTO next = iterator.next();
              if (DatasetFieldConstant.seriesName.equals(next.getTypeName())) {
                  seriesName = next.getSinglePrimitive();
              }
          }
          if (!seriesName.isEmpty()) {
              xmlw.writeStartElement("cit:series");
              xmlw.writeStartElement("cit:CI_Series");
              xmlw.writeStartElement("cit:name");
              xmlw.writeStartElement("gco:CharacterString");
              xmlw.writeCharacters(seriesName);
              xmlw.writeEndElement(); //gco:CharacterString
              xmlw.writeEndElement(); //cit:name
              xmlw.writeEndElement(); //cit:CI_Series
              xmlw.writeEndElement(); //cit:series
          }
        }
        //cit:series/cit:CI_Series/cit:name/gco:CharacterString
    }

    private void distributionDate(XMLStreamWriter xmlw, String distributionDate) throws XMLStreamException {
        xmlw.writeStartElement("cit:date");
        dateISO(xmlw, distributionDate, "distribution");
        xmlw.writeEndElement();//cit:date
    }

    private void geoReferenceDate(XMLStreamWriter xmlw, FieldDTO geoReferenceDateDTO) throws XMLStreamException {
        for (HashSet<FieldDTO> foo : geoReferenceDateDTO.getMultipleCompound()) {
            String geoReferenceDateType = "";
            String geoReferenceDateValue = "";

            for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {

                FieldDTO next = iterator.next();
                if (DatasetFieldConstant.geoReferenceDateType.equals(next.getTypeName())) {
                    geoReferenceDateType = next.getSinglePrimitive();
                }
                if (DatasetFieldConstant.geoReferenceDateValue.equals(next.getTypeName())) {
                    geoReferenceDateValue = next.getSinglePrimitive();
                }
            }
            if (!geoReferenceDateValue.isEmpty()) {
                xmlw.writeStartElement("cit:date");
                dateISO(xmlw, geoReferenceDateValue, geoReferenceDateType);
                xmlw.writeEndElement();//cit:date
            }
        }
    }

    private void dateISO(XMLStreamWriter xmlw, String date, String code) throws XMLStreamException {

        xmlw.writeStartElement("cit:CI_Date");
        xmlw.writeStartElement("cit:date");
        xmlw.writeStartElement("gco:DateTime");
        xmlw.writeCharacters(date);
        xmlw.writeEndElement(); //gco:DateTime
        xmlw.writeEndElement(); //cit:date
        xmlw.writeStartElement("cit:dateType");
        xmlw.writeStartElement("cit:CI_DateTypeCode");
        xmlw.writeAttribute("codeList","http://standards.iso.org/iso/19115/resources/Codelist/cat/codeLists.xml#CI_DateTypeCode");
        xmlw.writeAttribute("codeListValue", code);
        xmlw.writeCharacters(code);
        xmlw.writeEndElement(); //cit:CI_DateTypeCode
        xmlw.writeEndElement(); //cit:dateType
        xmlw.writeEndElement(); //cit:CI_Date

    }

    private void title(XMLStreamWriter xmlw, FieldDTO titleDTO) throws XMLStreamException {
        String title = titleDTO.getSinglePrimitive();
        xmlw.writeStartElement("cit:title");
        xmlw.writeStartElement("gco:CharacterString");
        xmlw.writeCharacters(title);
        xmlw.writeEndElement(); //gco:CharacterString
        xmlw.writeEndElement(); //cit:title
    }

    private void alternativeTitle(XMLStreamWriter xmlw, FieldDTO alternativeTitleDTO) throws XMLStreamException {
        if (alternativeTitleDTO != null) {
            for (String altTitle : alternativeTitleDTO.getMultiplePrimitive()) {
                xmlw.writeStartElement("cit:alternateTitle");
                xmlw.writeStartElement("gco:CharacterString");
                xmlw.writeCharacters(altTitle);
                xmlw.writeEndElement(); //gco:CharacterString
                xmlw.writeEndElement(); //cit:alternateTitle
            }
        }
    }

    private void responsibleParty(XMLStreamWriter xmlw, String name, String role) throws XMLStreamException {
        xmlw.writeStartElement("cit:citedResponsibleParty");
        xmlw.writeStartElement("cit:CI_Responsibility");
        xmlw.writeStartElement("cit:role");
        xmlw.writeStartElement("cit:CI_RoleCode");
        xmlw.writeAttribute("codeList", "http://standards.iso.org/iso/19115/resources/Codelist/cat/codeLists.xml#CI_RoleCode");
        xmlw.writeAttribute("codeListValue", role);
        xmlw.writeCharacters(role);
        xmlw.writeEndElement(); // cit:CI_RoleCode
        xmlw.writeEndElement(); // cit:role
        xmlw.writeStartElement("cit:party");
        xmlw.writeStartElement("cit:CI_Organisation");
        xmlw.writeStartElement("cit:name");
        xmlw.writeAttribute("xsi:type", "lan:PT_FreeText_PropertyType");
        xmlw.writeStartElement("gco:CharacterString");
        xmlw.writeCharacters(name);
        xmlw.writeEndElement(); //gco:CharacterString
        xmlw.writeEndElement(); //cit:name
        xmlw.writeEndElement(); //cit:CI_Organisation
        xmlw.writeEndElement(); //cit:party
        xmlw.writeEndElement(); //cit:CI_Responsibility
        xmlw.writeEndElement(); //cit:citedResponsibleParty
    }

    private void writeDescritiveKeywords(XMLStreamWriter xmlw, FieldDTO keywords) throws XMLStreamException {
        if (DatasetFieldConstant.keyword.equals(keywords.getTypeName())) {
            boolean isCVV = false;
            for (HashSet<FieldDTO> foo : keywords.getMultipleCompound()) {
                String keywordValue = "";
                String keywordVocab = "";
                String keywordURI = "";
                for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                    FieldDTO next = iterator.next();
                    if (DatasetFieldConstant.keywordValue.equals(next.getTypeName())) {
                        if (next.isControlledVocabularyField()) {
                            isCVV = true;
                        }
                        keywordValue = next.getSinglePrimitive();
                    }
                    if (DatasetFieldConstant.keywordVocab.equals(next.getTypeName())) {
                        keywordVocab = next.getSinglePrimitive();
                    }
                    if (DatasetFieldConstant.keywordVocabURI.equals(next.getTypeName())) {
                        keywordURI = next.getSinglePrimitive();
                    }
                }
                if (!keywordValue.isEmpty()) {

                    xmlw.writeStartElement("mri:descriptiveKeywords");
                    xmlw.writeStartElement("mri:keyword");
                    xmlw.writeStartElement("gco:CharacterString");
                    xmlw.writeCharacters(keywordValue);
                    xmlw.writeEndElement(); //gco:CharacterString
                    xmlw.writeEndElement(); //mri:keyword
                    xmlw.writeStartElement("mri:type");
                    xmlw.writeStartElement("mri:MD_KeywordTypeCode");
                    xmlw.writeAttribute("codeSpace","ISOTC211/19115");
                    xmlw.writeAttribute("codeList","http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_KeywordTypeCode");
                    xmlw.writeAttribute("codeListValue","theme");
                    xmlw.writeCharacters("theme");
                    xmlw.writeEndElement(); //mri:MD_KeywordTypeCode
                    xmlw.writeEndElement(); //mri:type
                    xmlw.writeStartElement("mri:thesaurusName");
                    xmlw.writeStartElement("cit:CI_Citation");
                    xmlw.writeStartElement("cit:title");
                    xmlw.writeStartElement("gco:CharacterString");
                    xmlw.writeCharacters(keywordVocab);
                    xmlw.writeEndElement(); //gco:CharacterString
                    xmlw.writeEndElement(); //cit:title
                    xmlw.writeStartElement("cit:presentationForm");
                    xmlw.writeStartElement("cit:CI_PresentationFormCode");
                    xmlw.writeAttribute("codeList","http://standards.iso.org/iso/19115/resources/Codelist/cat/codeLists.xml#CI_PresentationFormCode");
                    xmlw.writeAttribute("codeListValue","documentDigital");
                    xmlw.writeCharacters("documentDigital");
                    xmlw.writeEndElement(); //cit:CI_PresentationFormCode
                    xmlw.writeEndElement(); //cit:presentationForm
                    xmlw.writeEndElement(); //cit:CI_Citation
                    xmlw.writeEndElement(); //mri:thesaurusName
                    xmlw.writeEndElement(); //mri:descriptiveKeywords
                }
            }
        }

    }
    private void writeExtent(XMLStreamWriter xmlw, FieldDTO geographicBoundingBoxDTO) throws XMLStreamException {
        /* Only 1 geoBndBox is
           So, I'm just going to arbitrarily use the first one, and ignore the rest! */
        if (geographicBoundingBoxDTO != null) {
            HashSet<FieldDTO> bndBoxSet = geographicBoundingBoxDTO.getMultipleCompound().get(0);
            HashMap<String, String> geoBndBoxMap = new HashMap<>();
            for (FieldDTO next : bndBoxSet) {
                if (DatasetFieldConstant.westLongitude.equals(next.getTypeName())) {
                    geoBndBoxMap.put("westBL", next.getSinglePrimitive());
                }
                if (DatasetFieldConstant.eastLongitude.equals(next.getTypeName())) {
                    geoBndBoxMap.put("eastBL", next.getSinglePrimitive());
                }
                if (DatasetFieldConstant.northLatitude.equals(next.getTypeName())) {
                    geoBndBoxMap.put("northBL", next.getSinglePrimitive());
                }
                if (DatasetFieldConstant.southLatitude.equals(next.getTypeName())) {
                    geoBndBoxMap.put("southBL", next.getSinglePrimitive());
                }
            }
            xmlw.writeStartElement("mri:extent");
            xmlw.writeStartElement("gex:EX_Extent");
            xmlw.writeStartElement("gex:geographicElement");
            xmlw.writeStartElement("gex:EX_GeographicBoundingBox");

            if (geoBndBoxMap.get("westBL") != null) {
                xmlw.writeStartElement("gex:westBoundLongitude");
                xmlw.writeStartElement("gco:Decimal");
                xmlw.writeCharacters(geoBndBoxMap.get("westBL"));
                xmlw.writeEndElement(); //gco:Decimal
                xmlw.writeEndElement();//gex:westBoundLongitude
            }
            if (geoBndBoxMap.get("eastBL") != null) {
                xmlw.writeStartElement("gex:eastBoundLongitude");
                xmlw.writeStartElement("gco:Decimal");
                xmlw.writeCharacters(geoBndBoxMap.get("eastBL"));
                xmlw.writeEndElement(); //gco:Decimal
                xmlw.writeEndElement();//gex:eastBoundLongitude
            }
            if (geoBndBoxMap.get("southBL") != null) {
                xmlw.writeStartElement("gex:southBoundLatitude");
                xmlw.writeStartElement("gco:Decimal");
                xmlw.writeCharacters(geoBndBoxMap.get("southBL"));
                xmlw.writeEndElement(); //gco:Decimal
                xmlw.writeEndElement(); //gex:southBoundLatitude
            }

            if (geoBndBoxMap.get("northBL") != null) {
                xmlw.writeStartElement("gex:northBoundLatitude");
                xmlw.writeStartElement("gco:Decimal");
                xmlw.writeCharacters(geoBndBoxMap.get("northBL"));
                xmlw.writeEndElement(); //gco:Decimal
                xmlw.writeEndElement(); //gex:northBoundLatitude
            }

            xmlw.writeEndElement(); //ex:EX_GeographicBoundingBox
            xmlw.writeEndElement(); //gex:geographicElement
            xmlw.writeEndElement(); //gex:EX_Extent
            xmlw.writeEndElement(); //mri:extent
        }
    }

    private void writeReferenceSystemInfo(XMLStreamWriter xmlw, FieldDTO referenceSystemInfoDTO) throws XMLStreamException {
        //logger.info("writeReferenceSystemInfo");
        //logger.info(Long.toString(referenceSystemInfoDTO.getMultipleCompound().size()));
        if (referenceSystemInfoDTO != null) {
            for (HashSet<FieldDTO> foo : referenceSystemInfoDTO.getMultipleCompound()) {

                String referenceSystemCode = "";
                String referenceSystemCodeSpace = "";
                for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                    FieldDTO next = iterator.next();
                    if (DatasetFieldConstant.referenceSystemCode.equals(next.getTypeName())) {
                        referenceSystemCode = next.getSinglePrimitive();
                    }
                    if (DatasetFieldConstant.referenceSystemCodeSpace.equals(next.getTypeName())) {
                        referenceSystemCodeSpace = next.getSinglePrimitive();
                    }
                }

                xmlw.writeStartElement("mdb:referenceSystemInfo");
                xmlw.writeStartElement("mrs:MD_ReferenceSystem");
                xmlw.writeStartElement("mrs:referenceSystemIdentifier");
                xmlw.writeStartElement("mcc:MD_Identifier");
                xmlw.writeStartElement("mcc:code");
                xmlw.writeStartElement("gco:CharacterString");
                xmlw.writeCharacters(referenceSystemCode);
                xmlw.writeEndElement(); //gco:CharacterString
                xmlw.writeEndElement(); //mcc:code
                xmlw.writeStartElement("mcc:codeSpace");
                xmlw.writeStartElement("gco:CharacterString");
                xmlw.writeCharacters(referenceSystemCodeSpace);
                xmlw.writeEndElement(); //gco:CharacterString
                xmlw.writeEndElement(); //mcc:codeSpace
                xmlw.writeEndElement(); // mcc:MD_Identifier
                xmlw.writeEndElement(); //mrs:referenceSystemIdentifier
                xmlw.writeEndElement(); //mrs:MD_ReferenceSystem
                xmlw.writeEndElement(); //mdb:referenceSystemInfo
            }
        }
    }

    private void writeDistributionInfo(XMLStreamWriter xmlw, FieldDTO distributionDTO) throws XMLStreamException {
        if (distributionDTO != null) {
            ArrayList<HashSet<FieldDTO>> distibution = distributionDTO.getMultipleCompound();
            if (distibution.size() > 0) {
                xmlw.writeStartElement("mdb:distributionInfo");
                xmlw.writeStartElement("mrd:MD_Distribution");
                xmlw.writeStartElement("mrd:transferOptions");
                xmlw.writeStartElement("mrd:MD_DigitalTransferOptions");
                for (HashSet<FieldDTO> foo : distibution) {

                    String distributionLinkLabel = "";
                    String distributionLink = "";
                    String protocol = "";
                    for (Iterator<FieldDTO> iterator = foo.iterator(); iterator.hasNext(); ) {
                        FieldDTO next = iterator.next();
                        if (DatasetFieldConstant.distributionLinkLabel.equals(next.getTypeName())) {
                            distributionLinkLabel = next.getSinglePrimitive();
                        }
                        if (DatasetFieldConstant.distributionLink.equals(next.getTypeName())) {
                            distributionLink = next.getSinglePrimitive();
                        }
                        if (DatasetFieldConstant.protocol.equals(next.getTypeName())) {
                            protocol = next.getSinglePrimitive();
                        }
                    }

                    onLine(xmlw, distributionLinkLabel, distributionLink, protocol);

                }
                xmlw.writeEndElement(); //mrd:MD_DigitalTransferOptions
                xmlw.writeEndElement(); //mrd:transferOptions
                xmlw.writeEndElement(); //mrd:MD_Distribution
                xmlw.writeEndElement(); //mdb:distributionInfo

            }
        }
    }

    private void  onLine(XMLStreamWriter xmlw, String distributionLinkLabel, String distributionLink, String protocol) throws XMLStreamException {
        xmlw.writeStartElement("mrd:onLine");
        xmlw.writeStartElement("cit:CI_OnlineResource");
        xmlw.writeStartElement("cit:linkage");
        xmlw.writeStartElement("gco:CharacterString");
        xmlw.writeCharacters(distributionLink);
        xmlw.writeEndElement(); //gco:CharacterString
        xmlw.writeEndElement(); //cit:linkage
        xmlw.writeStartElement("cit:protocol");
        xmlw.writeStartElement("gco:CharacterString");
        xmlw.writeCharacters(protocol);
        xmlw.writeEndElement(); //gco:CharacterString
        xmlw.writeEndElement(); //cit:protocol
        xmlw.writeStartElement("cit:name");
        xmlw.writeStartElement("gco:CharacterString");
        xmlw.writeCharacters(distributionLinkLabel);
        xmlw.writeEndElement(); //gco:CharacterString
        xmlw.writeEndElement(); //cit:name
        xmlw.writeStartElement("cit:function");
        xmlw.writeStartElement("cit:CI_OnLineFunctionCode");
        xmlw.writeAttribute("codeList","http://standards.iso.org/iso/19115/resources/Codelist/cat/codeLists.xml#CI_OnLineFunctionCode" );
        xmlw.writeAttribute( "codeListValue","fileAccess");
        xmlw.writeCharacters("fileAccess");
        xmlw.writeEndElement(); //cit:CI_OnLineFunctionCode
        xmlw.writeEndElement(); //cit:function
        xmlw.writeEndElement(); //cit:CI_OnlineResource
        xmlw.writeEndElement(); //mrd:onLine
    }

    @Override
    public Boolean isHarvestable() {
        // No, we don't want this format to be harvested!
        // For datasets with tabular data the <data> portions of the DDIs
        // become huge and expensive to parse; even as they don't contain any
        // metadata useful to remote harvesters. -- L.A. 4.5
        return false;
    }

    @Override
    public Boolean isAvailableToUsers() {
        return true;
    }

    @Override
    public String getXMLNameSpace() {
        return DEFAULT_XML_NAMESPACE;
    }

    @Override
    public String getXMLSchemaLocation() {
        return DEFAULT_XML_SCHEMALOCATION;
    }

    @Override
    public String getXMLSchemaVersion() {
        return DEFAULT_XML_VERSION;
    }
}
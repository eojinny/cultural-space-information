package ddwu.mobile.finalproject.ma02_20201003;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class CultureSapceInfoXmlParser {
    private static final String TAG = "Parser";
    private XmlPullParser parser;

    private enum TagType {NONE, NUM , SUBJECTCODE, ADDR, FAC_NAME, X_COORD, Y_COORD , PHNE, HOMEPAGE,FAC_DESC };

    private final static String ROW_TAG = "row";
    private final static String NUM_TAG = "NUM";
    private final static String SUBJECTCODE_TAG = "SUBJCODE";
    private final static String ADDR_TAG = "ADDR";
    private final static String FAC_NAME_TAG = "FAC_NAME";
    private final static String X_COORD_TAG = "X_COORD";
    private final static String Y_COORD_TAG = "Y_COORD";
    private final static String PHNE_TAG = "PHNE";
    private final static String HOMEPAGE_TAG = "HOMEPAGE";
    private final static String FAC_DESC = "FAC_DESC";


    public CultureSapceInfoXmlParser()
    {
        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<CulturalSpaceInfoDTO> parse(String xml) {
        ArrayList<CulturalSpaceInfoDTO> resultList = new ArrayList();
        CulturalSpaceInfoDTO dto = null;
        TagType tagtype = TagType.NONE;

        try
        {
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if(tag.equals(ROW_TAG) )
                            dto=new CulturalSpaceInfoDTO();
                        else if(tag.equals(NUM_TAG))
                            tagtype= TagType.NUM;
                        else if(tag.equals(SUBJECTCODE_TAG))
                            tagtype= TagType.SUBJECTCODE;
                        else if(tag.equals(ADDR_TAG))
                            tagtype= TagType.ADDR;
                        else if(tag.equals(FAC_NAME_TAG))
                            tagtype= TagType.FAC_NAME;
                        else if(tag.equals(X_COORD_TAG))
                            tagtype= TagType.X_COORD;
                        else if(tag.equals(Y_COORD_TAG))
                            tagtype= TagType.Y_COORD;
                        else if(tag.equals(PHNE_TAG))
                            tagtype= TagType.PHNE;
                        else if(tag.equals(HOMEPAGE_TAG))
                            tagtype= TagType.HOMEPAGE;
                        else if(tag.equals(FAC_DESC))
                            tagtype= TagType.FAC_DESC;
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals(ROW_TAG))
                            resultList.add(dto);
                        break;
                    case XmlPullParser.TEXT:
                        switch (tagtype)
                        {
                            case NUM:
                                if(dto != null){
                                    dto.setNum(Integer.valueOf(parser.getText()));
                                }
                                break;
                            case SUBJECTCODE:
                                if(dto != null){
                                    dto.setSubjectCode(parser.getText());}
                                break;
                            case ADDR:
                                if(dto != null) {
                                    dto.setAddr(parser.getText());
                                }
                                break;
                            case FAC_NAME:
                                if(dto != null){
                                    dto.setFac_Name(parser.getText());}

                                break;
                            case X_COORD:
                                if(dto != null){
                                    dto.setX_Coord(Double.valueOf(parser.getText()));}

                                break;
                            case Y_COORD:
                                if(dto != null){
                                    dto.setY_Coord(Double.valueOf(parser.getText()));}

                                break;
                            case PHNE:
                                if(dto != null){
                                    dto.setPhne(parser.getText());}

                                break;
                            case HOMEPAGE:
                                if(dto != null){
                                    dto.setHomapage(parser.getText());}

                            case FAC_DESC:
                                if(dto != null){
                                    dto.setFac_Desc(parser.getText());}

                                break;
                        }
                        tagtype = TagType.NONE;
                        break;
                }
                eventType=parser.next();
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        Log.d(TAG, "ParserResult: " + resultList);
        return resultList;
    }



}

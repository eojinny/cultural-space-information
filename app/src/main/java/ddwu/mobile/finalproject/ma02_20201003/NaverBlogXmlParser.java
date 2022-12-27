package ddwu.mobile.finalproject.ma02_20201003;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class NaverBlogXmlParser {

    private XmlPullParser parser;

    private enum TagType { NONE, TITLE, DESCRIPTION, BLOGGERLINK};

    private final static String ITEM = "item";
    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";
    private final static String BLOGGERLINK = "bloggerlink";


    public NaverBlogXmlParser() {
        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NaverBlogDto> parse(String xml) {
        ArrayList<NaverBlogDto> resultList = new ArrayList<NaverBlogDto>();
        NaverBlogDto dto = null;
        TagType tagType = TagType.NONE;

        try{
            parser.setInput(new StringReader (xml));
            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();

                        if(tag.equals(ITEM))
                            dto = new NaverBlogDto();
                        else if(tag.equals(TITLE)) {
                            if(dto != null)
                                tagType = TagType.TITLE;
                        }
                        else if(tag.equals(DESCRIPTION)) {
                            if(dto != null)
                                tagType = TagType.DESCRIPTION;
                        }
                        else if(tag.equals(BLOGGERLINK))
                            tagType = TagType.BLOGGERLINK;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals(ITEM))
                            resultList.add(dto);
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType){
                            case TITLE:
                                if(dto != null)
                                    dto.setTitle(parser.getText());
                                break;
                            case DESCRIPTION:
                                if(dto != null)
                                    dto.setDescription (parser.getText());
                                break;
                            case BLOGGERLINK:
                                if(dto != null)
                                    dto.setBloggerlink(parser.getText());
                                break;
                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
}

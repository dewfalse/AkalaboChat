package net.tuyapin.alc.engine;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.tuyapin.alc.AkalaboChat;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class YahooConversionInput extends InputEngine
{

    public YahooConversionInput()
    {
        super(
                "http://jlp.yahooapis.jp/JIMService/V1/conversion" +
                "?appid=" + AkalaboChat.apikey +
                "&sentence="
            );
    }

    @Override
    public String getText(String text)
    {
        text = this.getResponse(text);

        StringBuilder builder = new StringBuilder();

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Node root = documentBuilder.parse(new ByteArrayInputStream(text.getBytes()));
            Node resultset = root.getFirstChild();
            Node result = resultset.getChildNodes().item(1);
            Node segmentlist = result.getChildNodes().item(1);

            NodeList segmentlistChildren = segmentlist.getChildNodes();

            for (int i = 0; i < segmentlistChildren.getLength(); i++) {
                Node a = segmentlistChildren.item(i);
                if(a.getNodeName().equals("Segment"))
                {
                    NodeList segmentChildren = a.getChildNodes();
                    label: for(int j = 0; j < segmentChildren.getLength(); j++)
                    {
                        Node b = segmentChildren.item(j);
                        if(b.getNodeName().equals("CandidateList"))
                        {
                            NodeList candidatelist = b.getChildNodes();
                            for (int k = 0; k < candidatelist.getLength(); k++)
                            {
                                Node c = candidatelist.item(k);
                                if(c.getNodeName().equals("Candidate")) {
                                    NodeList candidate = c.getChildNodes();
                                    builder.append(candidate.item(0).getNodeValue());
                                    break label;
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    @Override
    public String getEncode()
    {
        return "UTF8";
    }

}

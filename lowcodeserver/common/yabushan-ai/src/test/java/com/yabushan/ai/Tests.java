package com.yabushan.ai;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.word.WordInfo;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.List;

public class Tests {

    public static void main(String[] args) {
        String word ="我的生日是2024年4月11日";

      //  List<Term> segment = HanLP.segment(word);
        List<String> strings = HanLP.extractKeyword(word, 10);
        for (String s:strings
             ) {
            System.out.println(s);
        }
        /*for (Term t: segment
             ) {
            System.out.println(t.nature+":::"+t.word);
        }*/
        // 创建分词器
        Segment segment = HanLP.newSegment();
// 对文本进行分词并进行词性标注 
        List<Term> termList = segment.seg(word);
// 遍历分词结果，输出词汇和词性 
        for (Term term : termList) {    System.out.println(term.word + " " + term.nature); }
        String string="SELECT (SELECT COUNT(DISTINCT P.IMPLEMENT_RELATION_ID)\n" +
                "          FROM PFM_IMPLEMENT_PATH     P,\n" +
                "               PFM_IMPLEMENT_BASEINFO PIB,\n" +
                "               PFM_IMPLEMENT_RELATION PIR\n" +
                "         WHERE P.IMPLEMENT_ID = PIB.IMPLEMENT_ID\n" +
                "           and p.implement_relation_id = pir.implement_relation_id\n" +
                "           and (P.IS_DEAL_WITH = '0' OR\n" +
                "               (P.IS_DEAL_WITH = '1' AND P.IS_CUR_STEP = '1'))\n" +
                "           and P.IMPLEMENT_ID = '62723847'\n" +
                "           and P.STEP = '2'\n" +
                "           AND (P.CHECK_MAN_ID = '21020609')) \"allSub\",\n" +
                "       (SELECT COUNT(DISTINCT P1.IMPLEMENT_RELATION_ID)\n" +
                "          FROM PFM_IMPLEMENT_PATH     P1,\n" +
                "               PFM_IMPLEMENT_BASEINFO PIB,\n" +
                "               PFM_IMPLEMENT_RELATION PIR2\n" +
                "         WHERE P1.IS_CUR_STEP = 1\n" +
                "           AND P1.IMPLEMENT_ID = PIB.IMPLEMENT_ID\n" +
                "           and p1.implement_relation_id = pir2.implement_relation_id\n" +
                "           and P1.IMPLEMENT_ID = '62723847'\n" +
                "           and P1.STEP = '2'\n" +
                "           AND (P1.CHECK_MAN_ID = '21020609')) \"hasSub\"\n" +
                "  FROM DUAL ;";
        List<Term> termList1 = StandardTokenizer.segment(string);
        System.out.println(termList1);
        String text ="我的生日是2024年12月11日";
       // List<Term> termList2 = StandardTokenizer.segment(text);
      /*  token_res, ner_res = hanlp_model.extract_ner(content)
        print(token_res)
        print(ner_res)*/
        System.out.println(">>>>>>>>>>>>>>>>>>");
        List<String> strings1 = HanLP.extractKeyword(text,150);
        System.out.println(strings1);
        List<String> strings1q = HanLP.extractPhrase(text,150);
        System.out.println(strings1q);
        List<String> strings1q1 = HanLP.extractSummary(text,150);
        System.out.println(strings1q1);
        List<WordInfo> wordInfos = HanLP.extractWords(text, 50);
        System.out.println(wordInfos.toString());
        System.out.println("生日".length());

    }
}

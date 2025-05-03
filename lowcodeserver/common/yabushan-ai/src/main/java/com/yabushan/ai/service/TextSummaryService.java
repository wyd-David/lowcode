package com.yabushan.ai.service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Service
@Slf4j
public class TextSummaryService {

    public static void main(String[] args) {
        String text = "这是一段很长的文本。包含多个句子。每个句子都有不同的主题。我们需要从中提取重要的内容。生成一个简短的摘要。";
        TextSummaryService textSummaryService = new TextSummaryService();
        String summary = textSummaryService.generateSummary(text, 2);
        System.out.println("摘要：" + summary);
    }

    // 停用词表
  /*  private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "的", "了", "是", "在", "我", "有", "和", "就", "不", "人", "都", "一", "一个", "上", "也", "很", "到", "说", "要", "去", "你", "会", "着", "没有", "看", "好", "自己", "这"
            // 添加更多停用词...

    ));*/
    public static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            // 常用代词
            "我", "我们", "你", "你们", "他", "他们", "她", "她们", "它", "它们", "这", "这个", "那", "那个",
            "这些", "那些", "谁", "什么", "哪", "哪个", "哪些", "自己", "每个", "每位", "某", "某个",

            // 常用介词
            "在", "从", "向", "和", "与", "给", "对", "由", "把", "被", "让", "使", "为", "为了", "关于",

            // 常用连词
            "而且", "并且", "或者", "不过", "然而", "但是", "可是", "如果", "虽然", "因为", "所以", "的话",

            // 时间词
            "时候", "现在", "过去", "将来", "今天", "明天", "昨天", "年", "月", "日", "时", "分", "秒",
            "早上", "上午", "中午", "下午", "晚上", "凌晨", "深夜", "以前", "以后", "之前", "之后",

            // 频率词
            "常常", "时常", "往往", "偶尔", "经常", "很少", "从不", "从来", "一直", "总是", "已经", "曾经",

            // 数量词
            "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "零", "百", "千", "万", "亿",
            "第一", "第二", "第三", "第四", "第五", "第六", "第七", "第八", "第九", "第十",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "几", "多少", "一些", "部分", "大量", "许多", "很多", "若干",

            // 方位词
            "上", "下", "左", "右", "前", "后", "里", "外", "内", "中", "间", "旁", "边", "东", "西", "南", "北",

            // 助词
            "的", "地", "得", "着", "了", "过", "吗", "呢", "啊", "吧", "么", "嘛", "呀", "哇", "哦", "噢", "哎",

            // 副词
            "很", "非常", "特别", "格外", "分外", "十分", "极其", "更加", "还", "仍然", "依然", "又", "也", "都",
            "才", "就", "只", "可能", "大概", "或许", "必定", "当然", "一定", "肯定", "确实", "果然",

            // 语气词
            "吗", "呢", "吧", "啊", "么", "嘛", "哈", "呀", "哇", "哦", "噢", "喔", "嗯", "嘿", "哼", "唉",

            // 否定词
            "不", "没", "没有", "别", "莫", "勿", "未", "非", "无", "不要", "不能", "不可以",

            // 程度词
            "最", "更", "更加", "比较", "稍微", "略微", "几乎", "差不多", "恰恰", "极其", "极度", "极端",

            // 常用动词
            "是", "有", "无", "没有", "存在", "成为", "保持", "变成", "属于", "包含", "包括", "等于",
            "做", "去", "来", "说", "想", "看", "觉得", "认为", "需要", "希望", "要", "可以", "能", "会",

            // 其他常用词
            "这样", "那样", "如此", "般", "种", "样", "此", "该", "这么", "那么", "如何", "怎样", "怎么",
            "一样", "一般", "似的", "好像", "如同", "当", "比", "像", "例如", "譬如", "对于", "来说",

            // 标点符号
            "。", "，", "、", "；", "：", "？", "！", "…", "—", "－", "（", "）", "《", "》",
            "「", "」", "『", "』", "〈", "〉", "【", "】", "［", "］", "～", "‖", "∶", "＂", "＇", "｀",

            // 英文常用词
            "the", "a", "an", "and", "or", "but", "in", "on", "at", "to", "for", "of", "with",
            "by", "from", "up", "down", "out", "about", "into", "over", "after", "is", "are",
            "was", "were", "be", "been", "being", "have", "has", "had", "do", "does", "did",
            "will", "would", "shall", "should", "may", "might", "must", "can", "could",

            // 特殊符号
            " ", "\t", "\n", "\r", "\"", "'", "`", "-", "_", "+", "=", "*", "/", "\\", "|",
            "@", "#", "$", "%", "^", "&", "(", ")", "[", "]", "{", "}", "<", ">", "~"
    ));

    /**
     * 生成文本摘要
     */
    public String generateSummary(String text, int summaryLength) {
        try {
            // 1. 分句
            List<String> sentences = splitIntoSentences(text);
            if (sentences.size() <= summaryLength) {
                return text;
            }

            // 2. 计算词频
            Map<String, Integer> wordFreq = calculateWordFrequency(sentences);

            // 3. 计算句子得分
            List<SentenceScore> scoredSentences = new ArrayList<>();
            int totalSentences = sentences.size();

            for (int i = 0; i < sentences.size(); i++) {
                String sentence = sentences.get(i);
                // 计算句子得分 (结合TF-IDF和位置权重)
                double score = calculateSentenceScore(sentence, wordFreq, i, totalSentences);
                scoredSentences.add(new SentenceScore(sentence, score, i));
            }

            // 4. 选择得分最高的句子
            return selectTopSentences(scoredSentences, summaryLength);

        } catch (Exception e) {
            log.error("生成摘要失败", e);
            return text;
        }
    }

    /**
     * 分句
     */
    private List<String> splitIntoSentences(String text) {
        return Arrays.stream(text.split("[。！？.!?]+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 分词并计算词频
     */
    private Map<String, Integer> calculateWordFrequency(List<String> sentences) {
        Map<String, Integer> wordFreq = new HashMap<>();

        for (String sentence : sentences) {
            // 简单分词 (按空格和标点分词)
            String[] words = sentence.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9]", " ")
                    .split("\\s+");

            for (String word : words) {
                if (word.length() > 1 && !STOP_WORDS.contains(word)) {
                    wordFreq.merge(word, 1, Integer::sum);
                }
            }
        }

        return wordFreq;
    }

    /**
     * 计算句子得分
     */
    private double calculateSentenceScore(String sentence, Map<String, Integer> wordFreq,
                                          int position, int totalSentences) {
        // 1. 计算词频得分
        double tfScore = 0;
        String[] words = sentence.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9]", " ")
                .split("\\s+");

        for (String word : words) {
            if (word.length() > 1 && !STOP_WORDS.contains(word)) {
                tfScore += wordFreq.getOrDefault(word, 0);
            }
        }

        // 2. 位置权重 (开头和结尾的句子更重要)
        double positionWeight;
        if (position == 0 || position == totalSentences - 1) {
            positionWeight = 1.5;
        } else if (position <= totalSentences * 0.1 || position >= totalSentences * 0.9) {
            positionWeight = 1.2;
        } else {
            positionWeight = 1.0;
        }

        // 3. 句子长度权重 (过短或过长的句子权重降低)
        double lengthWeight = 1.0;
        if (words.length < 5) {
            lengthWeight = 0.8;
        } else if (words.length > 30) {
            lengthWeight = 0.7;
        }

        return tfScore * positionWeight * lengthWeight;
    }

    /**
     * 选择得分最高的句子
     */
    private String selectTopSentences(List<SentenceScore> scoredSentences, int summaryLength) {
        // 按得分排序
        scoredSentences.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        // 选择前N个句子
        List<SentenceScore> selectedSentences = scoredSentences.subList(0,
                Math.min(summaryLength, scoredSentences.size()));

        // 按原文顺序排序
        selectedSentences.sort(Comparator.comparingInt(SentenceScore::getPosition));

        // 组合摘要
        return selectedSentences.stream()
                .map(SentenceScore::getSentence)
                .collect(Collectors.joining("。")) + "。";
    }
}

@Data
@AllArgsConstructor
class SentenceScore {
    private String sentence;
    private double score;
    private int position;
}

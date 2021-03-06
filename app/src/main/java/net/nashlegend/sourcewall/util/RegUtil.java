package net.nashlegend.sourcewall.util;

import android.text.Html;

/**
 * Created by NashLegend on 2014/9/15 0015
 */
public class RegUtil {

    public static String tryGetStringByLength(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len) + "...";
        } else {
            return str;
        }
    }

    public static String clearHtmlByTag(String content, String tag) {
        return content.replaceAll("<" + tag + ">" + ".*?" + "</" + tag + ">", "");
    }

    public static String clearHtmlBlockQuote(String content) {
        return clearHtmlByTag(content, "blockquote");
    }

    /**
     * Html转纯文本无格式不带换行，但是保留图片标签
     *
     * @param content
     *
     * @return
     */
    public static String html2PlainTextWithImageTag(String content) {
        return Html.fromHtml(content.replaceAll("<img .*?/>|<img.*?>.*?</img>", "[图片]")).toString().replaceAll("\n", "");
    }

    /**
     * Html转纯文本无格式不带换行
     *
     * @param content
     *
     * @return
     */
    public static String html2PlainText(String content) {
        return Html.fromHtml(content).toString().replaceAll("\n", "");
    }

    /**
     * Html转纯文本无格式不带换行除去引用块
     *
     * @param content
     *
     * @return
     */
    public static String html2PlainTextWithoutBlockQuote(String content) {
        return html2PlainText(clearHtmlBlockQuote(content));
    }
}

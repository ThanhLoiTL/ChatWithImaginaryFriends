package com.android.chatwithimaginaryfriends.util;

import com.android.chatwithimaginaryfriends.model.InteractionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ResponseChat {
    public static String response(List<InteractionModel> interactionList, String text) {
        String reply = "";
        int maxWordSame = 0;
        List<String> listResponse = new ArrayList<>();
        String message = text.toLowerCase();
        if(message.length() > 1) {
            for (InteractionModel interaction : interactionList) {
                List<String> listWord = ConvertUtil.stringToArray(interaction.getTriggerWord());
                List<String> listReply = ConvertUtil.stringToArray(interaction.getReplyWord());

                for (String word : listWord) {
                    if (message.contains(word.toLowerCase())) {
//                    int count = countWord(word);
//                    if(count > max) {
//                        max = count;
//                        reply = listReply.get(new Random().nextInt(listReply.size()));
//                    }
                        listResponse.addAll(listReply);
                        break;
                    }
                }
            }
            if (!listResponse.isEmpty()) {
                reply = listResponse.get(new Random().nextInt(listResponse.size()));
            } else {
                for (InteractionModel interaction : interactionList) {
                    List<String> listWord = ConvertUtil.stringToArray(interaction.getTriggerWord());
                    List<String> listReply = ConvertUtil.stringToArray(interaction.getReplyWord());

                    for (String words : listWord) {
                        String[] word = words.split(" ");
                        int countWordSame = 0;
                        for (String w : word) {
                            if (w.length() > 1 && message.contains(w.toLowerCase())) {
                                countWordSame++;
                            }
                        }
                        if (countWordSame > maxWordSame) {
                            maxWordSame = countWordSame;
                            //reply = listReply.get(new Random().nextInt(listReply.size()));
                        }
                    }
                }
                for (InteractionModel interaction : interactionList) {
                    List<String> listWord = ConvertUtil.stringToArray(interaction.getTriggerWord());
                    List<String> listReply = ConvertUtil.stringToArray(interaction.getReplyWord());

                    for (String words : listWord) {
                        String[] word = words.split(" ");
                        int countWordSame = 0;
                        for (String w : word) {
                            if (w.length() > 1 && message.contains(w.toLowerCase())) {
                                countWordSame++;
                            }
                        }
                        if (countWordSame == maxWordSame) {
                            listResponse.addAll(listReply);
                        }
                    }
                }
                if (!listResponse.isEmpty()) {
                    reply = listResponse.get(new Random().nextInt(listResponse.size()));
                }
            }
        }
        return reply;
    }

    private static int countWord(String str) {
        int count = 0;
        for(int i =0; i<str.length()-1;i++){
            if(str.charAt(i) == ' ' && str.charAt(i+1) != ' '){
                count++;
            }
            if(str.charAt(0) != ' '){
                count++;
            }
        }
        return count;
    }
}

package com.android.chatwithimaginaryfriends.util;

import com.android.chatwithimaginaryfriends.model.InteractionModel;

import java.math.BigInteger;
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
                    if (message.equals(word.toLowerCase())) {
                        listResponse.addAll(listReply);
                        break;
                    }
                }
            }
            if (!listResponse.isEmpty()) {
                reply = listResponse.get(new Random().nextInt(listResponse.size()));
            } else {
                String[] messages = message.trim().split(" ");
                for (InteractionModel interaction : interactionList) {
                    List<String> listWord = ConvertUtil.stringToArray(interaction.getTriggerWord());
                    for (String words : listWord) {
                        String[] word = words.trim().split(" ");
                        int countWordSame = 0;
                        for (String m : messages) {
                            for (String w : word) {
                                if (m.length() > 1 && w.length() > 1 && m.equalsIgnoreCase(w.toLowerCase())) {
                                    countWordSame++;
                                }
                            }
                            if (countWordSame > maxWordSame) {
                                maxWordSame = countWordSame;
                            }
                        }
                    }
                }
                for (InteractionModel interaction : interactionList) {
                    List<String> listWord = ConvertUtil.stringToArray(interaction.getTriggerWord());
                    List<String> listReply = ConvertUtil.stringToArray(interaction.getReplyWord());

                    for (String words : listWord) {
                        String[] word = words.split(" ");
                        int countWordSame = 0;
                        for (String m : messages) {
                            for (String w : word) {
                                if (m.length() > 1 && w.length() > 1 && m.equalsIgnoreCase(w.toLowerCase())) {
                                    countWordSame++;
                                }
                            }
                            if (countWordSame == maxWordSame && maxWordSame > 1) {
                                listResponse.addAll(listReply);
                            }
                        }
                    }
                }
                System.out.println(maxWordSame);
                if (!listResponse.isEmpty()) {
                    System.out.println("\n");
                    for (String i: listResponse) {

                        System.out.println(i);
                    }
                    reply = listResponse.get(new Random().nextInt(listResponse.size()));
                }
            }
        }
        return reply;
    }
}

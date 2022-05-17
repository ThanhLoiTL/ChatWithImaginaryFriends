package com.android.chatwithimaginaryfriends.util;

import com.android.chatwithimaginaryfriends.model.InteractionModel;
import java.util.List;
import java.util.Random;

public class ResponseChat {
    public static String response(List<InteractionModel> interactionList, String text) {
        String reply = "";
        int max = 0;
        String message = text.toLowerCase();
        for (InteractionModel interaction: interactionList) {
            List<String> listWord = ConvertUtil.stringToArray(interaction.getTriggerWord());
            List<String> listReply = ConvertUtil.stringToArray(interaction.getReplyWord());

            for (String word: listWord) {
                if(message.contains(word.toLowerCase())){
                    int count = countWord(word);
                    if(count > max) {
                        max = count;
                        reply = listReply.get(new Random().nextInt(listReply.size()));
                    }
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

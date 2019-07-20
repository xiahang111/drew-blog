package com.drew.controller;


import com.drew.entity.ResponseResult;
import com.drew.item.dto.DrewEverydaySentenceDTO;
import com.drew.item.pojo.DrewEverdaySentence;
import com.drew.service.DrewSentenceService;
import com.drew.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("sentence")
public class DrewSentenceController {


    @Autowired
    private DrewSentenceService drewSentenceService;


    @RequestMapping("getAll")
    public ResponseResult getAllSentence() {
        List<DrewEverdaySentence> everdaySentences = new ArrayList<>();

        everdaySentences = drewSentenceService.getAllsentence();

        if (null == everdaySentences || everdaySentences.size() <= 0) {
            return ResponseResult.error;
        }

        List<DrewEverydaySentenceDTO> sentenceDTOS = new ArrayList<>();

        for (DrewEverdaySentence sentence : everdaySentences) {
            sentenceDTOS.add(new DrewEverydaySentenceDTO(sentence));

        }

        return new ResponseResult(sentenceDTOS);
    }

    @RequestMapping("getToday")
    public ResponseResult getTodaySentence(){

        DrewEverydaySentenceDTO sentenceDTO = drewSentenceService.getTodaySentence();

        if(null == sentenceDTO){

            if(null == sentenceDTO){

                sentenceDTO = new DrewEverydaySentenceDTO();
                sentenceDTO.setShowTime(DateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_CHINESE));
                sentenceDTO.setContent("世界上总有人要赢的，反正我肯定赢不了");
                sentenceDTO.setCreateTime(DateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_CHINESE));
                sentenceDTO.setUpdateTime(DateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_CHINESE));

                return new ResponseResult(sentenceDTO);
            }

        }

        return new ResponseResult(sentenceDTO);

    }
}

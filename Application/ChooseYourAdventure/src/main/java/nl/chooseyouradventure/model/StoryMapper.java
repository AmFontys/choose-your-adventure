package nl.chooseyouradventure.model;

import nl.chooseyouradventure.model.dta.StoryBodyTypeDta;
import nl.chooseyouradventure.model.dta.StoryDta;
import nl.chooseyouradventure.model.dta.StorybodyDta;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.model.entity.Story;
import nl.chooseyouradventure.model.entity.StoryBodyType;
import nl.chooseyouradventure.model.entity.Storybody;
import nl.chooseyouradventure.model.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StoryMapper {
   public List<Story> giveEntityStory(List<StoryDta> dtaList){
       if(dtaList ==null)return null;
       List<Story> returnedList= new ArrayList<>();
        for(StoryDta dta: dtaList){
            if(dta!=null) {
                User userEntity = Usermapper.giveEntity(dta.getUser());

                if (userEntity != null)
                    returnedList.add(Story.builder()
                            .storyid(dta.getStoryid())
                            .title(dta.getTitle())
                            .user(userEntity)
                            .build());
            }
        }
        return  returnedList;
    }

    public static Story giveEntityStory(StoryDta dta){
       if(dta==null)return null;
     User userEntity= Usermapper.giveEntity(dta.getUser());
     return Story.builder()
             .storyid(dta.getStoryid())
             .title(dta.getTitle())
             .user(userEntity)
             .build();
    }

    public StoryDta giveDtaStory(Story story){
       if(story==null)return null;
     UserDta userDta= Usermapper.giveDta(story.getUser());

    return StoryDta.builder()
             .storyid(story.getStoryid())
             .title(story.getTitle())
             .user(userDta)
             .build();

    }

    public Optional<StoryDta> giveDtaStory(Optional<Story> story){
       if(!story.isEmpty()&& story.isPresent()) {
           UserDta userDta = Usermapper.giveDta(story.get().getUser());
           if(userDta == null || story.get().getTitle()==null) return Optional.empty();
           return Optional.ofNullable(StoryDta.builder()
                   .storyid(story.get().getStoryid())
                   .title(story.get().getTitle())
                   .user(userDta)
                   .build());
       }
       return Optional.empty();
    }

    public List<StoryDta> giveDtaStory(List<Story> storyList) {
        List<StoryDta> returnedList = new ArrayList<>();
        if (storyList != null)
            for (Story story : storyList) {
                if (story != null) {
                    UserDta userDta = Usermapper.giveDta(story.getUser());
                    if (userDta != null) {
                        returnedList.add(StoryDta.builder()
                                .storyid(story.getStoryid())
                                .title(story.getTitle())
                                .user(userDta)
                                .build());
                    }
                }
            }
        return returnedList;
    }

    /********StoryBody******/
    public List<Storybody> giveEntityStorybody(List<StorybodyDta> dtaList){
     List<Storybody> returnedList= new ArrayList<>();
     for(StorybodyDta dta: dtaList){
         if(dta!=null) {
             Story story = this.giveEntityStory(dta.getStory());
             StoryBodyType type = giveEntityStorybodyType(dta.getType());
             if (story != null && type != null) {
                 returnedList.add(Storybody.builder()
                         .id(dta.getId())
                         .story(story)
                         .bodyTitle(dta.getBodyTitle())
                         .text(dta.getText())
                         .type(type)
                         .chosen(dta.getChosen())
                         .build());
             }
         }

     }
     return  returnedList;
    }


 public static Storybody giveEntityStorybody(StorybodyDta dta){
        if (dta==null || dta.getType()==null)return null;
  Story story= giveEntityStory(dta.getStory());
  StoryBodyType type = giveEntityStorybodyType(dta.getType());
if(story==null) return null;
  return Storybody.builder()
          .id(dta.getId())
          .story(story)
          .bodyTitle(dta.getBodyTitle())
          .text(dta.getText())
          .type(type)
          .chosen(dta.getChosen())
          .build();
    }

    public StorybodyDta giveDtaStorybody(Storybody storybody) {
        if(storybody==null) return null;
        StoryDta story = giveDtaStory(storybody.getStory());

        if (storybody.getType() == null || story == null) return null;
        StoryBodyTypeDta type = giveDtaStorybodyType(storybody.getType());

        return StorybodyDta.builder()
                .id(storybody.getId())
                .story(story)
                .bodyTitle(storybody.getBodyTitle())
                .text(storybody.getText())
                .type(type)
                .chosen(storybody.getChosen())
                .build();
    }

    public List<StorybodyDta> giveDtaStorybody(List<Storybody> storybodyList) {
        List<StorybodyDta> returnedList = new ArrayList<>();

        if (storybodyList != null) {
            List<Storybody> filteredList=   storybodyList.stream().filter( storybody -> storybody!=null && storybody.getStory()!=null && storybody.getType()!=null).collect(Collectors.toList());

            for (Storybody storybody : filteredList) {
                StoryDta story = this.giveDtaStory(storybody.getStory());
                StoryBodyTypeDta type = giveDtaStorybodyType(storybody.getType());

                returnedList.add(StorybodyDta.builder()
                        .id(storybody.getId())
                        .story(story)
                        .bodyTitle(storybody.getBodyTitle())
                        .text(storybody.getText())
                        .type(type)
                        .chosen(storybody.getChosen())
                        .build());

            }
        }
        return returnedList;
    }

    /******Story body type***/
    public static StoryBodyType giveEntityStorybodyType(StoryBodyTypeDta type) {
        if(type==null || type.getTypename()==null)return null;
     return StoryBodyType.builder()
             .storyBodyTypeId(type.getStorybody_typeId())
             .typename(type.getTypename())
             .build();
    }
 public StoryBodyTypeDta giveDtaStorybodyType(StoryBodyType type) {
     if (type == null || type.getTypename() == null) return null;
     return StoryBodyTypeDta.builder()
             .storybody_typeId(type.getStoryBodyTypeId())
             .typename(type.getTypename())
             .build();
 }

    public List<StoryBodyTypeDta> giveDtaStorybodyType(List<StoryBodyType> all) {
        List<StoryBodyTypeDta> returnList = new ArrayList<>();
        if (all != null)
            for (StoryBodyType type : all) {
                if (type != null && type.getTypename() != null)
                    returnList.add(StoryBodyTypeDta.builder()
                            .storybody_typeId(type.getStoryBodyTypeId())
                            .typename(type.getTypename())
                            .build());
            }
        return returnList;
    }
}

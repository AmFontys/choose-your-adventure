package nl.chooseyouradventure.model;

import nl.chooseyouradventure.model.dta.StoryBodyTypeDta;
import nl.chooseyouradventure.model.dta.StoryDta;
import nl.chooseyouradventure.model.dta.StorybodyDta;
import nl.chooseyouradventure.model.entity.Story;
import nl.chooseyouradventure.model.entity.StoryBodyType;
import nl.chooseyouradventure.model.entity.Storybody;
import nl.chooseyouradventure.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StoryMapperTest {

    StoryMapper storyMapper = new StoryMapper();

    User userEntity = User.builder().userid(200)
            .username("Ramen")
            .password("passwordEncoded")
            .keyword("key")
            .email("ramen@gmail.com")
            .ismod(false).build();
    Story storyEntity;
    StoryDta storyDta;

    List<Story> storyList;
    List<StoryDta> storyDtaList;

    Storybody storybodyEntity;
    StorybodyDta storybodyDta;
    List<Storybody> storybodyList;
    List<StorybodyDta> storybodyDtaList;

    StoryBodyType typeEntity;
    StoryBodyTypeDta typeDta;
    List<StoryBodyType> typeList;
    List<StoryBodyTypeDta> typeDtaList;

    private void loadStoryObjects(){
        storyEntity=Story.builder()
                .storyid(1)
                .title("Amazing title")
                .user(userEntity)
                .build();

        storyDta=StoryDta.builder() .storyid(1)
                .title("Amazing title")
                //It's debatable if this is the wrong way to do this because
                // there is a separate test for the User mapper and in the actual methods this is also done like this
                .user(Usermapper.giveDta( userEntity))
                .build();

        storyList=new ArrayList<>();
        storyList.add(storyEntity);
        storyDtaList=new ArrayList<>();
        storyDtaList.add(storyDta);
    }

    private void loadStoryBodyObjects(){
        loadStoryObjects();
        loadStoryBodyTypeObjects();
        storybodyEntity=Storybody.builder().bodyTitle("title of amzing")
                .type(typeEntity)
                .story(storyEntity)
                .text("Introduction text")
                .build();

        storybodyDta=StorybodyDta.builder().bodyTitle("title of amzing")
                .type(typeDta)
                .story(storyDta)
                .text("Introduction text")
                .build();

        storybodyList=new ArrayList<>();
        storybodyList.add(storybodyEntity);
        storybodyDtaList=new ArrayList<>();
        storybodyDtaList.add(storybodyDta);
    }

    private void loadStoryBodyTypeObjects(){
        typeEntity=StoryBodyType.builder().storyBodyTypeId(1).typename("Intro").build();

        typeDta=StoryBodyTypeDta.builder().storybody_typeId(1).typename("Intro").build();

        typeList=new ArrayList<>();
        typeList.add(typeEntity);
        typeDtaList=new ArrayList<>();
        typeDtaList.add(typeDta);
    }

    @Test
    void giveEntityStoryList() {
        // given - precondition or setup
        loadStoryObjects();
        // when -  action or the behaviour that we are going test
        List<Story> actualList= storyMapper.giveEntityStory(storyDtaList);
        // then - verify the output
        assertThat(actualList).isEqualTo(storyList);
    }

    @Test
    void giveEntityStory() {
        // given - precondition or setup
        loadStoryObjects();
        // when -  action or the behaviour that we are going test
        Story actualStory = StoryMapper.giveEntityStory(storyDta);
        // then - verify the output
        assertThat(actualStory).isEqualTo(storyEntity);
    }

    @Test
    void giveDtaStory() {
        // given - precondition or setup
        loadStoryObjects();
        // when -  action or the behaviour that we are going test
        StoryDta actualStoryDta = storyMapper.giveDtaStory(storyEntity);
        // then - verify the output
        assertThat(actualStoryDta).isEqualTo(storyDta);
    }

    @Test
    void giveDtaStoryOptional() {
        // given - precondition or setup
        loadStoryObjects();
        // when -  action or the behaviour that we are going test
        Optional<StoryDta> actualStoryDta = storyMapper.giveDtaStory(Optional.of(storyEntity));
        // then - verify the output
        assertThat(actualStoryDta).isEqualTo(Optional.of(storyDta));
    }

    @Test
    void giveDtaStoryList() {
        // given - precondition or setup
        loadStoryObjects();
        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStoryDtaList = storyMapper.giveDtaStory(storyList);
        // then - verify the output
        assertThat(actualStoryDtaList).isEqualTo(storyDtaList);
    }

    @Test
    void giveEntityStorybodyList() {
        // given - precondition or setup
        loadStoryBodyObjects();
        // when -  action or the behaviour that we are going test
        List<Storybody> actualBodyList = storyMapper.giveEntityStorybody(storybodyDtaList);
        // then - verify the output
        assertThat(actualBodyList).isEqualTo(storybodyList);
    }

    @Test
    void giveEntityStorybody() {
        // given - precondition or setup
        loadStoryBodyObjects();
        // when -  action or the behaviour that we are going test
        Storybody actualBody = storyMapper.giveEntityStorybody(storybodyDta);
        // then - verify the output
        assertThat(actualBody).isEqualTo(storybodyEntity);
    }

    @Test
    void giveDtaStorybody() {
        // given - precondition or setup
        loadStoryBodyObjects();
        // when -  action or the behaviour that we are going test
        StorybodyDta actualBodyDta = storyMapper.giveDtaStorybody(storybodyEntity);
        // then - verify the output
        assertThat(actualBodyDta).isEqualTo(storybodyDta);
    }

    @Test
    void giveDtaStorybodyList() {
        // given - precondition or setup
        loadStoryBodyObjects();
        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualBodyDtaList = storyMapper.giveDtaStorybody(storybodyList);
        // then - verify the output
        assertThat(actualBodyDtaList).isEqualTo(storybodyDtaList);
    }

    @Test
    void giveEntityStorybodyType() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        // when -  action or the behaviour that we are going test
        StoryBodyType actualBodyType = storyMapper.giveEntityStorybodyType(typeDta);
        // then - verify the output
        assertThat(actualBodyType).isEqualTo(typeEntity);
    }

    @Test
    void giveDtaStorybodyType() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        // when -  action or the behaviour that we are going test
        StoryBodyTypeDta actualBodyTypeDta = storyMapper.giveDtaStorybodyType(typeEntity);
        // then - verify the output
        assertThat(actualBodyTypeDta).isEqualTo(typeDta);
    }

    @Test
    void giveDtaStorybodyTypeList() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        // when -  action or the behaviour that we are going test
        List<StoryBodyTypeDta> actualBodyTypeDtaList = storyMapper.giveDtaStorybodyType(typeList);
        // then - verify the output
        assertThat(actualBodyTypeDtaList).isEqualTo(typeDtaList);
    }

    /*****Null recieving *******/

    @Test
    void giveEntityStoryList_GivenNull() {
        // given - precondition or setup
        loadStoryObjects();

        storyDtaList=null;
        // when -  action or the behaviour that we are going test
        List<Story> actualStory = storyMapper.giveEntityStory(storyDtaList);
        // then - verify the output
        assertThat(actualStory).isNull();
    }
    @Test
    void giveEntityStoryList_AllNull() {
        // given - precondition or setup
        loadStoryObjects();
        storyDtaList.clear();
        storyDtaList.add(null);
        storyDtaList.add(null);

        // when -  action or the behaviour that we are going test
        List<Story> actualStory = storyMapper.giveEntityStory(storyDtaList);
        // then - verify the output
        assertThat(actualStory).isNullOrEmpty();

    }
    @Test
    void giveEntityStoryList_OneNull() {
        // given - precondition or setup
        loadStoryObjects();
        storyDtaList.remove(0);
        storyDtaList.add(storyDta);
        storyDtaList.add(null);
        storyDtaList.add(storyDta);

        storyList.add(storyEntity);
        // when -  action or the behaviour that we are going test
        List<Story> actualStory = storyMapper.giveEntityStory(storyDtaList);
        // then - verify the output
        assertThat(actualStory).isEqualTo(storyList);
        assertThat(actualStory).hasSize(2);
    }

    @Test
    void giveEntityStory_GivenNull() {
        // given - precondition or setup

        StoryDta dta=null;
        // when -  action or the behaviour that we are going test
        Story actualStory = storyMapper.giveEntityStory(dta);
        // then - verify the output
        assertThat(actualStory).isNull();
    }

    @Test
    void giveDtaStory_GivenNull() {
        // given - precondition or setup

        Story entity=null;
        // when -  action or the behaviour that we are going test
        StoryDta actualStory = storyMapper.giveDtaStory(entity);
        // then - verify the output
        assertThat(actualStory).isNull();
    }

    @Test
    void giveDtaStoryOptional_GivenNull() {
        // given - precondition or setup

        Optional<Story> entity=Optional.empty();
        // when -  action or the behaviour that we are going test
        Optional<StoryDta> actualStory = storyMapper.giveDtaStory(entity);
        // then - verify the output
        assertThat(actualStory).isEqualTo(Optional.empty());
    }

    @Test
    void giveDtaStoryList_GivenNull() {
        // given - precondition or setup
        loadStoryObjects();
        storyList=null;
        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStoryDtaList = storyMapper.giveDtaStory(storyList);
        // then - verify the output
        assertThat(actualStoryDtaList).isNullOrEmpty();
    }

    @Test
    void giveDtaStoryList_AllNull() {
        // given - precondition or setup
        loadStoryObjects();
        storyList.clear();
        storyList.add(null);
        storyList.add(null);
        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStoryDtaList = storyMapper.giveDtaStory(storyList);
        // then - verify the output
        assertThat(actualStoryDtaList).isNullOrEmpty();
    }
    @Test
    void giveDtaStoryList_OneNull() {
        // given - precondition or setup
        loadStoryObjects();
        storyList.add(storyEntity);
        storyList.add(storyEntity);
        storyList.add(storyEntity);
        storyList.add(null);
        storyList.add(storyEntity);
        storyList.add(storyEntity);

        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStoryDtaList = storyMapper.giveDtaStory(storyList);
        // then - verify the output
        assertThat(actualStoryDtaList).isNotNull();
        assertThat(actualStoryDtaList).hasSize(6);
    }

    @Test
    void giveEntityStorybodyList_GivenNull() {
        // given - precondition or setup
        loadStoryObjects();
        storyDtaList=null;
        // when -  action or the behaviour that we are going test
        List<Story> actualStoryList = storyMapper.giveEntityStory(storyDtaList);
        // then - verify the output
        assertThat(actualStoryList).isEqualTo(null);
    }
    @Test
    void giveEntityStorybodyList_AllNull() {
        // given - precondition or setup
        loadStoryObjects();
        storyDtaList.clear();
        storyDtaList.add(null);
        storyDtaList.add(null);
        // when -  action or the behaviour that we are going test
        List<Story> actualStoryList = storyMapper.giveEntityStory(storyDtaList);
        // then - verify the output
        assertThat(actualStoryList).isNullOrEmpty();
    }
    @Test
    void giveEntityStorybodyList_OneNull() {
        // given - precondition or setup
        loadStoryObjects();
        storyDtaList.add(storyDta);
        storyDtaList.add(storyDta);
        storyDtaList.add(storyDta);
        storyDtaList.add(null);
        storyDtaList.add(storyDta);
        storyDtaList.add(storyDta);

        // when -  action or the behaviour that we are going test
        List<Story> actualStoryList = storyMapper.giveEntityStory(storyDtaList);
        // then - verify the output
        assertThat(actualStoryList).isNotNull();
        assertThat(actualStoryList).hasSize(6);
    }
    @Test
    void giveEntityStorybody_GivenNull() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyDta=null;
        // when -  action or the behaviour that we are going test
        Storybody actualBody = storyMapper.giveEntityStorybody(storybodyDta);
        // then - verify the output
        assertThat(actualBody).isNull();
    }

    @Test
    void giveDtaStorybody_GivenNull() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyEntity=null;
        // when -  action or the behaviour that we are going test
        StorybodyDta actualBodyDta = storyMapper.giveDtaStorybody(storybodyEntity);
        // then - verify the output
        assertThat(actualBodyDta).isNull();
    }

    @Test
    void giveDtaStorybodyList_GivenNull() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyList=null;
        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualBodyDtaList = storyMapper.giveDtaStorybody(storybodyList);
        // then - verify the output
        assertThat(actualBodyDtaList).isNullOrEmpty();
    }
    @Test
    void giveDtaStorybodyList_GivenAllNull() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyList.clear();
        storybodyList=null;
        storybodyList=null;
        storybodyList=null;
        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualBodyDtaList = storyMapper.giveDtaStorybody(storybodyList);
        // then - verify the output
        assertThat(actualBodyDtaList).isNullOrEmpty();
    }
    @Test
    void giveDtaStorybodyList_GivenOneNull() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyList.add(storybodyEntity);
        storybodyList.add(storybodyEntity);
        storybodyList.add(null);
        storybodyList.add(storybodyEntity);
        storybodyList.add(storybodyEntity);

        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualBodyDtaList = storyMapper.giveDtaStorybody(storybodyList);
        // then - verify the output
        assertThat(actualBodyDtaList).isNotNull();
        assertThat(actualBodyDtaList).hasSize(5);
    }


    @Test
    void giveEntityStorybodyType_GivenNull() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        typeDta=null;
        // when -  action or the behaviour that we are going test
        StoryBodyType actualBodyType = storyMapper.giveEntityStorybodyType(typeDta);
        // then - verify the output
        assertThat(actualBodyType).isNull();
    }

    @Test
    void giveDtaStorybodyType_GivenNull() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        typeEntity=null;
        // when -  action or the behaviour that we are going test
        StoryBodyTypeDta actualBodyTypeDta = storyMapper.giveDtaStorybodyType(typeEntity);
        // then - verify the output
        assertThat(actualBodyTypeDta).isNull();
    }

    @Test
    void giveDtaStorybodyTypeList_GivenNull() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        typeList=null;
        // when -  action or the behaviour that we are going test
        List<StoryBodyTypeDta> actualBodyTypeDtaList = storyMapper.giveDtaStorybodyType(typeList);
        // then - verify the output
        assertThat(actualBodyTypeDtaList).isNullOrEmpty();
    }

    @Test
    void giveDtaStorybodyTypeList_AllNull() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        typeList.clear();
        typeList.add(null);
        typeList.add(null);
        typeList.add(null);
        // when -  action or the behaviour that we are going test
        List<StoryBodyTypeDta> actualBodyTypeDtaList = storyMapper.giveDtaStorybodyType(typeList);
        // then - verify the output
        assertThat(actualBodyTypeDtaList).isNullOrEmpty();
    }
    @Test
    void giveDtaStorybodyTypeList_OneNull() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        typeList.clear();
        typeList.add(typeEntity);
        typeList.add(null);
        typeList.add(typeEntity);
        // when -  action or the behaviour that we are going test
        List<StoryBodyTypeDta> actualBodyTypeDtaList = storyMapper.giveDtaStorybodyType(typeList);
        // then - verify the output
        assertThat(actualBodyTypeDtaList).isNotNull();
        assertThat(actualBodyTypeDtaList).hasSize(2);
    }
    /*****One missing of build *****/

    @Test
    void giveEntityStoryList_MissingUser() {
        // given - precondition or setup
        loadStoryObjects();
        storyDtaList.get(0).setUser(null);
        // when -  action or the behaviour that we are going test
        List<Story> actualList= storyMapper.giveEntityStory(storyDtaList);
        // then - verify the output
        assertThat(actualList).isNotEqualTo(storyList);
    }

    @Test
    void giveEntityStoryList_OneMissingUser() {
        // given - precondition or setup
        loadStoryObjects();
        StoryDta missingUser= StoryDta.builder()
                .storyid(storyEntity.getStoryid())
                .title(storyEntity.getTitle())
                .user(null).build();

        storyDtaList.add(storyDta);
        storyDtaList.add(missingUser);
        storyDtaList.add(storyDta);

        storyList.add(storyEntity);
        storyList.add(storyEntity);
        // when -  action or the behaviour that we are going test
        List<Story> actualList= storyMapper.giveEntityStory(storyDtaList);
        // then - verify the output
        assertThat(actualList).isEqualTo(storyList);
        assertThat(actualList).hasSize(3);
    }

    @Test
    void giveEntityStory_MissingUser() {
        // given - precondition or setup
        loadStoryObjects();
        storyDta.setUser(null);
        // when -  action or the behaviour that we are going test
        Story actualStory = storyMapper.giveEntityStory(storyDta);
        // then - verify the output
        assertThat(actualStory).isNotEqualTo(storyEntity);
    }

    @Test
    void giveDtaStory_MissingUser() {
        // given - precondition or setup
        loadStoryObjects();
        storyEntity.setUser(null);
        // when -  action or the behaviour that we are going test
        StoryDta actualStoryDta = storyMapper.giveDtaStory(storyEntity);
        // then - verify the output
        assertThat(actualStoryDta).isNotEqualTo(storyDta);
    }

    @Test
    void giveDtaStoryOptional_MissingUser() {
        // given - precondition or setup

        loadStoryObjects();
        storyEntity.setUser(null);
        // when -  action or the behaviour that we are going test
        Optional<StoryDta> actualStoryDta = storyMapper.giveDtaStory(Optional.of(storyEntity));
        // then - verify the output
        assertThat(actualStoryDta).isNotEqualTo(Optional.of(storyDta));
    }

    @Test
    void giveDtaStoryOptional_MissingStory() {
        // given - precondition or setup
        loadStoryObjects();
        Story intermediateStoryEntity= storyEntity;
        intermediateStoryEntity.setTitle(null);
        // when -  action or the behaviour that we are going test
        Optional<StoryDta> actualStoryDta = storyMapper.giveDtaStory(Optional.of(intermediateStoryEntity));
        // then - verify the output
        assertThat(actualStoryDta).isNotEqualTo(Optional.of(storyDta));
        assertThat(actualStoryDta).isEmpty();
    }

    @Test
    void giveDtaStoryList_MissingUser() {
        // given - precondition or setup
        loadStoryObjects();
        storyList.get(0).setUser(null);
        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStoryDtaList = storyMapper.giveDtaStory(storyList);
        // then - verify the output
        assertThat(actualStoryDtaList).isNotEqualTo(storyDtaList);
    }

    @Test
    void giveDtaStoryList_MultipleMissingUser() {
        // given - precondition or setup
        loadStoryObjects();
        Story missingUser= Story.builder()
                .storyid(storyEntity.getStoryid())
                .title(storyEntity.getTitle())
                .user(null).build();

        storyList.add(storyEntity);
        storyList.add(missingUser);
        storyList.add(missingUser);
        storyList.add(storyEntity);



        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStoryDtaList = storyMapper.giveDtaStory(storyList);
        // then - verify the output
        assertThat(actualStoryDtaList).isNotEqualTo(storyDtaList);
        assertThat(actualStoryDtaList).hasSize(3);
    }

    @Test
    void giveEntityStorybodyList_MissingStory() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyDtaList.get(0).setStory(null);
        // when -  action or the behaviour that we are going test
        List<Storybody> actualBodyList = storyMapper.giveEntityStorybody(storybodyDtaList);
        // then - verify the output
        assertThat(actualBodyList).isNotEqualTo(storybodyList);
    }

    @Test
    void giveEntityStorybodyList_BetweenMissingStory() {
        // given - precondition or setup
        loadStoryBodyObjects();
        StorybodyDta missingStory = StorybodyDta.builder()
                .id(storybodyDta.getId())
                .text(storybodyDta.getText())
                .story(null)
                .type(storybodyDta.getType())
                .bodyTitle(storybodyDta.getBodyTitle())
                .build();

        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(missingStory);
        storybodyDtaList.add(storybodyDta);

        // when -  action or the behaviour that we are going test
        List<Storybody> actualBodyList = storyMapper.giveEntityStorybody(storybodyDtaList);
        // then - verify the output
        assertThat(actualBodyList).isNotEqualTo(storybodyList);
        assertThat(actualBodyList).hasSize(4);
    }

    @Test
    void giveEntityStorybodyList_MultipleMissingStory() {
        // given - precondition or setup
        loadStoryBodyObjects();
        StorybodyDta missingStory = StorybodyDta.builder()
                .id(storybodyDta.getId())
                .text(storybodyDta.getText())
                .story(null)
                .type(storybodyDta.getType())
                .bodyTitle(storybodyDta.getBodyTitle())
                .build();

        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(missingStory);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(missingStory);
        storybodyDtaList.add(storybodyDta);


        // when -  action or the behaviour that we are going test
        List<Storybody> actualBodyList = storyMapper.giveEntityStorybody(storybodyDtaList);
        // then - verify the output
        assertThat(actualBodyList).isNotEqualTo(storybodyList);
        assertThat(actualBodyList).hasSize(4);
    }
    @Test
    void giveEntityStorybodyList_MissingType() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyDtaList.get(0).setType(null);
        // when -  action or the behaviour that we are going test
        List<Storybody> actualBodyList = storyMapper.giveEntityStorybody(storybodyDtaList);
        // then - verify the output
        assertThat(actualBodyList).isNotEqualTo(storybodyList);
    }
    @Test
    void giveEntityStorybodyList_BetweenMissingType() {
        // given - precondition or setup
        loadStoryBodyObjects();
        StorybodyDta missingType = StorybodyDta.builder()
                .id(storybodyDta.getId())
                .text(storybodyDta.getText())
                .story(storybodyDta.getStory())
                .type(null)
                .bodyTitle(storybodyDta.getBodyTitle())
                .build();

        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(missingType);
        storybodyDtaList.add(storybodyDta);

        // when -  action or the behaviour that we are going test
        List<Storybody> actualBodyList = storyMapper.giveEntityStorybody(storybodyDtaList);
        // then - verify the output
        assertThat(actualBodyList).isNotEqualTo(storybodyList);
        assertThat(actualBodyList).hasSize(4);
    }
    @Test
    void giveEntityStorybodyList_MultipleMissingType() {
        // given - precondition or setup
        loadStoryBodyObjects();
        StorybodyDta isNull = StorybodyDta.builder()
                .id(storybodyDta.getId())
                .text(storybodyDta.getText())
                .story(storybodyDta.getStory())
                .type(null)
                .bodyTitle(storybodyEntity.getBodyTitle())
                .build();

        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);

        storybodyDtaList.set(0,isNull);
        storybodyDtaList.set(1,isNull);
        storybodyDtaList.set(2,isNull);
        // when -  action or the behaviour that we are going test
        List<Storybody> actualBodyList = storyMapper.giveEntityStorybody(storybodyDtaList);
        // then - verify the output
        assertThat(actualBodyList).isNotEqualTo(storybodyList);
        assertThat(actualBodyList).hasSize(3);
    }

    @Test
    void giveEntityStorybody_MissingStory() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyDta.setStory(null);
        // when -  action or the behaviour that we are going test
        Storybody actualBody = storyMapper.giveEntityStorybody(storybodyDta);
        // then - verify the output
        assertThat(actualBody).isNotEqualTo(storybodyEntity);
        assertThat(actualBody).isNull();
    }

    @Test
    void giveEntityStorybody_MissingType() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyDta.setType(null);
        // when -  action or the behaviour that we are going test
        Storybody actualBody = storyMapper.giveEntityStorybody(storybodyDta);
        // then - verify the output
        assertThat(actualBody).isNotEqualTo(storybodyEntity);
        assertThat(actualBody).isNull();
    }

    @Test
    void giveDtaStorybody_MissingStory() {
        // given - precondition or setup
        loadStoryBodyObjects();
        Storybody intermediateBodyEntity= storybodyEntity;
        intermediateBodyEntity.setStory(null);
        // when -  action or the behaviour that we are going test
        StorybodyDta actualBodyDta = storyMapper.giveDtaStorybody(intermediateBodyEntity);
        // then - verify the output
        assertThat(actualBodyDta).isNotEqualTo(storybodyDta);
        assertThat(actualBodyDta).isNull();
    }
    @Test
    void giveDtaStorybody_MissingType() {
        // given - precondition or setup
        loadStoryBodyObjects();
        Storybody intermediateBodyEntity= storybodyEntity;
        intermediateBodyEntity.setType(null);
        // when -  action or the behaviour that we are going test
        StorybodyDta actualBodyDta = storyMapper.giveDtaStorybody(intermediateBodyEntity);
        // then - verify the output
        assertThat(actualBodyDta).isNotEqualTo(storybodyDta);
        assertThat(actualBodyDta).isNull();
    }
    @Test
    void giveDtaStorybodyList_MissingStory() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyList.get(0).setStory(null);
        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualBodyDtaList = storyMapper.giveDtaStorybody(storybodyList);
        // then - verify the output
        assertThat(actualBodyDtaList).isNotEqualTo(storybodyDtaList);
    }
    @Test
    void giveDtaStorybodyList_BetweenMissingStory() {
        // given - precondition or setup
        loadStoryBodyObjects();

        Storybody setNull =Storybody.builder()
                .id(storybodyEntity.getId())
                .text(storybodyEntity.getText())
                .story(null)
                .type(storybodyEntity.getType())
                .bodyTitle(storybodyEntity.getBodyTitle())
                .build();

        storybodyList.add(storybodyEntity);
        storybodyList.add(storybodyEntity);
        storybodyList.add(storybodyEntity);

        storybodyList.set(2,setNull);
        storybodyEntity.setStory(storyEntity);
        storybodyList.add(storybodyEntity);
        storybodyList.add(storybodyEntity);

        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);

        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualBodyDtaList = storyMapper.giveDtaStorybody(storybodyList);
        // then - verify the output
        assertThat(actualBodyDtaList).isEqualTo(storybodyDtaList);
        assertThat(actualBodyDtaList).hasSize(5);
    }
    @Test
    void giveDtaStorybodyList_MultipleMissingStory() {
        // given - precondition or setup
        loadStoryBodyObjects();
        Storybody setNull =Storybody.builder()
                .id(storybodyEntity.getId())
                .text(storybodyEntity.getText())
                .story(null)
                .type(storybodyEntity.getType())
                .bodyTitle(storybodyEntity.getBodyTitle())
                .build();

        storybodyList.add(storybodyEntity);
        storybodyList.add(storybodyEntity);
        storybodyList.add(setNull);
        storybodyList.add(storybodyEntity);
        storybodyList.add(storybodyEntity);
        storybodyList.add(setNull);
        storybodyList.add(storybodyEntity);

        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualBodyDtaList = storyMapper.giveDtaStorybody(storybodyList);
        // then - verify the output
        assertThat(actualBodyDtaList).isEqualTo(storybodyDtaList);
        assertThat(actualBodyDtaList).hasSize(6);
    }

    @Test
    void giveDtaStorybodyList_MissingType() {
        // given - precondition or setup
        loadStoryBodyObjects();
        storybodyList.get(0).setType(null);
        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualBodyDtaList = storyMapper.giveDtaStorybody(storybodyList);
        // then - verify the output
        assertThat(actualBodyDtaList).isNotEqualTo(storybodyDtaList);
        assertThat(actualBodyDtaList).isNullOrEmpty();
    }
    @Test
    void giveDtaStorybodyList_BetweenMissingType() {
        // given - precondition or setup
        loadStoryBodyObjects();
        Storybody missingType = Storybody.builder()
                .id(storybodyEntity.getId())
                .text(storybodyEntity.getText())
                .story(storybodyEntity.getStory())
                .type(null)
                .bodyTitle(storybodyEntity.getBodyTitle())
                .build();

        storybodyList.add(storybodyEntity);
        storybodyList.add(storybodyEntity);
        storybodyList.add(missingType);
        storybodyList.add(storybodyEntity);
        storybodyList.add(storybodyEntity);

        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);
        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualBodyDtaList = storyMapper.giveDtaStorybody(storybodyList);
        // then - verify the output
        assertThat(actualBodyDtaList).isEqualTo(storybodyDtaList);
        assertThat(actualBodyDtaList).hasSize(5);
    }
    @Test
    void giveDtaStorybodyList_MultipleMissingType() {
        // given - precondition or setup
        loadStoryBodyObjects();
        Storybody missingType = Storybody.builder()
                .id(storybodyEntity.getId())
                .text(storybodyEntity.getText())
                .story(storybodyEntity.getStory())
                .type(null)
                .bodyTitle(storybodyEntity.getBodyTitle())
                .build();

        storybodyList.add(storybodyEntity);
        storybodyList.add(missingType);
        storybodyList.add(missingType);
        storybodyList.add(storybodyEntity);
        storybodyList.add(missingType);

        storybodyDtaList.add(storybodyDta);
        storybodyDtaList.add(storybodyDta);

        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualBodyDtaList = storyMapper.giveDtaStorybody(storybodyList);
        // then - verify the output
        assertThat(actualBodyDtaList).isEqualTo(storybodyDtaList);
        assertThat(actualBodyDtaList).hasSize(3);
    }
    @Test
    void giveEntityStorybodyType_MissingName() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        typeDta.setTypename(null);
        // when -  action or the behaviour that we are going test
        StoryBodyType actualBodyType = storyMapper.giveEntityStorybodyType(typeDta);
        // then - verify the output
        assertThat(actualBodyType).isNotEqualTo(typeEntity);
        assertThat(actualBodyType).isNull();
    }

    @Test
    void giveDtaStorybodyType_MissingName() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        StoryBodyType intermediateTypeEntity=typeEntity;
        intermediateTypeEntity.setTypename(null);

        // when -  action or the behaviour that we are going test
        StoryBodyTypeDta actualBodyTypeDta = storyMapper.giveDtaStorybodyType(intermediateTypeEntity);
        // then - verify the output
        assertThat(actualBodyTypeDta).isNotEqualTo(typeDta);
        assertThat(actualBodyTypeDta).isNull();
    }

    @Test
    void giveDtaStorybodyTypeList_MissingName() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();

        typeList.get(0).setTypename(null);
        // when -  action or the behaviour that we are going test
        List<StoryBodyTypeDta> actualBodyTypeDtaList = storyMapper.giveDtaStorybodyType(typeList);
        // then - verify the output
        assertThat(actualBodyTypeDtaList).isNotEqualTo(typeDtaList);
        assertThat(actualBodyTypeDtaList).isNullOrEmpty();
    }

    @Test
    void giveDtaStorybodyTypeList_BetweenMissingName() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        StoryBodyType missingName = StoryBodyType.builder()
                .storyBodyTypeId(typeEntity.getStoryBodyTypeId())
                .typename(null)
                .build();

        typeList.add(typeEntity);
        typeList.add(missingName);
        typeList.add(typeEntity);
        typeList.add(typeEntity);

        // when -  action or the behaviour that we are going test
        List<StoryBodyTypeDta> actualBodyTypeDtaList = storyMapper.giveDtaStorybodyType(typeList);
        // then - verify the output
        assertThat(actualBodyTypeDtaList).isNotEqualTo(typeDtaList);
        assertThat(actualBodyTypeDtaList).hasSize(4);
    }

    @Test
    void giveDtaStorybodyTypeList_MultipleMissingName() {
        // given - precondition or setup
        loadStoryBodyTypeObjects();
        StoryBodyType missingName = StoryBodyType.builder()
                .storyBodyTypeId(typeEntity.getStoryBodyTypeId())
                .typename(null)
                .build();

        typeList.add(missingName);
        typeList.add(missingName);
        typeList.add(typeEntity);
        typeList.add(missingName);

        // when -  action or the behaviour that we are going test
        List<StoryBodyTypeDta> actualBodyTypeDtaList = storyMapper.giveDtaStorybodyType(typeList);
        // then - verify the output
        assertThat(actualBodyTypeDtaList).isNotEqualTo(typeDtaList);
        assertThat(actualBodyTypeDtaList).hasSize(2);
    }
}
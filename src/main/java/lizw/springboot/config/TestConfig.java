package lizw.springboot.config;

import lizw.springboot.entity.MemberEntity;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MemberEntity.class)
public class TestConfig {

    /**
     * @date 2019/3/31 20:22
     * @param No such property: code for class: Script1
     * @return
     */
    public void testConfig(){
        System.out.print("心累");
    }
}

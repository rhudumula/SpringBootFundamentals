package ttl.larku.trickyness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

interface Trick {
    public void doTrick();
}

@Order(2)
@Component
//@Qualifier("us-east")
//@Profile("dev")
class Tricky1 implements Trick {

    @Override
    public void doTrick() {
        System.out.println("Hand Stand");
    }
}

@Order(1)
@Component
//@Profile("prod")
@Qualifier("us-west")
class Tricky2 implements Trick {

    @Override
    public void doTrick() {
        System.out.println("Somersault");
    }
}

@Order(1)
@Component
//@Profile("prod")
@Qualifier("us-west")
class Tricky3 implements Trick {

    @Override
    public void doTrick() {
        System.out.println("Somersault");
    }
}

@Service
class Circus
{
    @Autowired
//    @Resource(name = "tricky2")
//    @Qualifier("tricky2")
    private Tricky2 tricky1;

    @Autowired
    @Qualifier("us-west")
    private List<Trick> allTricks;

    public void startShow() {
        allTricks.forEach(t -> t.doTrick());
//        tricky1.doTrick();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("prod");

        context.scan("ttl.larku.trickyness");
        context.refresh();

        Circus circus = context.getBean("circus", Circus.class);
        circus.startShow();

    }
}

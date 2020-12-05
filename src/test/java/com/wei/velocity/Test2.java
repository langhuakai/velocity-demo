package com.wei.velocity;

import com.wei.velocity.generator.Generator;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

public class Test2 {

    @Test
    public void test() {
        Generator generator=new Generator();
        String path = "templates/test.txt.vm";
        String paths = path.replace(".vm", "");
        File file = new File(path);
        String fileName = file.getName();
        String name = fileName.replace(".vm", "");
        try {
            generator.generator(1,"templates/test.txt.vm","E:/test4/template/" + name,new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

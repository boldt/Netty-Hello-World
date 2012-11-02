package de.dennis_boldt.netty.ressoures;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Test {

    @XmlAttribute
    Integer id = -1;

    // JAXB needs this
    public Test() {}

    public Test(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}

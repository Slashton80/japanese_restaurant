package ca.hccis.bus.pass.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusPassTest {

    @org.junit.jupiter.api.Test
    void testCalculateCost_length20() {
        BusPass busPass = new BusPass();
        busPass.setLengthOfPass(20);
        double actual = busPass.calculateCost();
        Assertions.assertEquals(20,actual);
    }

    @org.junit.jupiter.api.Test
    void testCalculateCost_length30() {
        BusPass busPass = new BusPass();
        busPass.setLengthOfPass(30);
        double actual = busPass.calculateCost();
        Assertions.assertEquals(25,actual);
    }

    @org.junit.jupiter.api.Test
    void testCalculateCost_length20_WITH_RURAL() {
        BusPass busPass = new BusPass();
        busPass.setLengthOfPass(20);
        busPass.setValidForRuralRoute(true);
        double actual = busPass.calculateCost();
        Assertions.assertEquals(30,actual);
    }

}
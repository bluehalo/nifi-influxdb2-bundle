package bluehalo.nifi.influxdb2;

import com.google.common.collect.ImmutableMap;
import com.influxdb.client.write.Point;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PutInfluxDBv2Test {

    @Test
    public void test_parse_tags() {
        String s = "a=A,b=B";
        Map<String, String> tags = PutInfluxDBv2.KeyValueStringValidator.parse(s);
        assertEquals("A", tags.get("a"));
        assertEquals("B", tags.get("b"));
    }

    @Test
    public void test_empty_string() {
        Point point = Point
                .measurement("measurement")
                .addFields(ImmutableMap.of("value", 1.0));

        String s = "a=A,b=,C=c,D=";
        point = PutInfluxDBv2.addTagsToPoint(point, s);
        assertEquals("measurement,C=c,a=A value=1.0", point.toLineProtocol());
    }
}
package hu.unideb.timi15.mybookshelf.mapper;

import com.google.cloud.Timestamp;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateConverter {

    @Named("timestampToLocalDate")
    public static LocalDate convertTimestampToLocalDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Named("localDateToTimestamp")
    public static Timestamp convertLocalDateToTimestamp(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        long epochSecond = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        return Timestamp.ofTimeSecondsAndNanos(epochSecond, 0);
    }
}

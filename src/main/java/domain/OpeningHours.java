package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OpeningHours {

    private static final Map<String, DayOfWeek> DAY_MAP = Map.of(
            "Mo", DayOfWeek.MONDAY,
            "Tu", DayOfWeek.TUESDAY,
            "We", DayOfWeek.WEDNESDAY,
            "Th", DayOfWeek.THURSDAY,
            "Fr", DayOfWeek.FRIDAY,
            "Sa", DayOfWeek.SATURDAY,
            "Su", DayOfWeek.SUNDAY);

    private static final int DAYS_IN_WEEK = 7;
    private static final String DASH = "-";
    private static final String SEMICOLON = ";";

    private final String raw;

    public OpeningHours(String raw) {
        this.raw = raw;
    }

    /**
     * Returns the current availability status based on the raw opening hours string.
     * @return  "Open", "Closed", or "N/A" based on current time and raw opening hours string.
     */
    public String availabilityStatus() {
        String result = "N/A";
        if (raw != null && !raw.isBlank()) {
            final String trimmed = raw.trim();
            if ("24/7".equalsIgnoreCase(trimmed)) {
                result = "Open";
            }
            else {
                try {
                    final boolean openNow = isOpenNow(trimmed);
                    if (openNow) {
                        result = "Open";
                    }
                    else {
                        result = "Closed";
                    }
                }
                catch (DateTimeParseException | IllegalArgumentException ignored) {
                    result = "Closed";
                }
            }
        }
        return result;
    }

    private boolean isOpenNow(String openingHours) {
        boolean isOpen = false;
        final LocalDateTime now = LocalDateTime.now();
        final DayOfWeek currentDay = now.getDayOfWeek();
        final LocalTime currentTime = now.toLocalTime();

        final String[] entries = openingHours.split(SEMICOLON);
        for (String entry : entries) {
            if (!entry.isBlank()) {
                final boolean entryApplies = entryAppliesNow(entry.trim(), currentDay, currentTime);
                if (entryApplies) {
                    isOpen = true;
                    break;
                }
            }
        }
        return isOpen;
    }

    private boolean entryAppliesNow(String entry, DayOfWeek currentDay, LocalTime currentTime) {
        boolean applies = false;
        final String[] parts = entry.split(" ", 2);
        if (parts.length == 2) {
            final Set<DayOfWeek> days = parseDays(parts[0]);
            if (days.contains(currentDay)) {
                final String[] times = parts[1].split(DASH, 2);
                if (times.length == 2) {
                    try {
                        final LocalTime start = LocalTime.parse(times[0]);
                        final LocalTime end = LocalTime.parse(times[1]);
                        applies = isTimeWithinRange(currentTime, start, end);
                    }
                    catch (DateTimeParseException ignored) {
                        //
                    }
                }
            }
        }
        return applies;
    }

    private boolean isTimeWithinRange(LocalTime currentTime, LocalTime start, LocalTime end) {
        final boolean withinRange;
        final boolean overnight = end.isBefore(start);

        final boolean afterStart = !currentTime.isBefore(start);
        final boolean beforeEnd = !currentTime.isAfter(end);
        if (overnight) {
            withinRange = afterStart || beforeEnd;
        }
        else {
            withinRange = afterStart && beforeEnd;
        }
        return withinRange;
    }

    private Set<DayOfWeek> parseDays(String dayPart) {
        final Set<DayOfWeek> days = new HashSet<>();
        if (dayPart != null && !dayPart.isBlank()) {
            final String[] ranges = dayPart.split(",");
            for (String range : ranges) {
                addDayRange(days, range.trim());
            }
        }
        return days;
    }

    private void addDayRange(Set<DayOfWeek> days, String range) {
        if (range.contains(DASH)) {
            final String[] bounds = range.split(DASH, 2);
            if (bounds.length == 2 && DAY_MAP.containsKey(bounds[0]) && DAY_MAP.containsKey(bounds[1])) {
                final int start = DAY_MAP.get(bounds[0]).getValue();
                final int end = DAY_MAP.get(bounds[1]).getValue();
                int i = start;
                boolean done = false;
                while (!done) {
                    days.add(DayOfWeek.of(i));
                    done = i == end;
                    if (!done) {
                        i = i % DAYS_IN_WEEK + 1;
                    }
                }
            }
        }
        else if (DAY_MAP.containsKey(range)) {
            days.add(DAY_MAP.get(range));
        }
    }
}

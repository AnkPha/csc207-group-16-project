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

    private final String raw;

    public OpeningHours(String raw) {
        this.raw = raw;
    }

    /**
     * Returns "Open", "Closed", or "N/A" based on current time and raw opening hours string.
     */
    public String availabilityStatus() {
        String status = "N/A";

        if (raw != null && !raw.isBlank()) {
            final String trimmed = raw.trim();
            if ("24/7".equalsIgnoreCase(trimmed)) {
                status = "Open"; // special case handled here but also in DAO
            } else {
                boolean openNow = false;
                try {
                    openNow = isOpenNow(trimmed);
                } catch (Exception error) {
                    openNow = false;
                }
                if (openNow) {
                    status = "Open";
                } else {
                    status = "Closed";
                }
            }
        }
        return status;
    }

    private boolean isOpenNow(String openingHours) {
        boolean appliesNow = false;
        final LocalDateTime now = LocalDateTime.now();
        final DayOfWeek currentDay = now.getDayOfWeek();
        final LocalTime currentTime = now.toLocalTime();

        final String[] entries = openingHours.split(";");
        for (String entry : entries) {
            if (!appliesNow) {
                final String newEntry = entry.trim();
                if (!newEntry.isEmpty()) {
                    appliesNow = entryAppliesNow(newEntry, currentDay, currentTime);
                }
            }
        }
        return appliesNow;
    }

    private boolean entryAppliesNow(String entry, DayOfWeek currentDay, LocalTime currentTime) {
        boolean applies = false;
        final String[] parts = entry.split(" ", 2);
        if (parts.length == 2) {
            final Set<DayOfWeek> days = parseDays(parts[0]);
            if (days.contains(currentDay)) {
                final String[] times = parts[1].split("-", 2);
                if (times.length == 2) {
                    try {
                        final LocalTime start = LocalTime.parse(times[0]);
                        final LocalTime end = LocalTime.parse(times[1]);
                        applies = isTimeWithinRange(currentTime, start, end);
                    } catch (DateTimeParseException ignored) {
                        applies = false;
                    }
                }
            }
        }
        return applies;
    }

    private boolean isTimeWithinRange(LocalTime currentTime, LocalTime start, LocalTime end) {
        boolean withinRange = false;

        if (end.isBefore(start)) {
            withinRange = !currentTime.isBefore(start) || !currentTime.isAfter(end);
        } else {
            withinRange = !currentTime.isBefore(start) && !currentTime.isAfter(end);
        }

        return withinRange;
    }

    private Set<DayOfWeek> parseDays(String dayPart) {
        final Set<DayOfWeek> days = new HashSet<>();

        if (dayPart != null && !dayPart.isBlank()) {
            final String[] ranges = dayPart.split(",");
            for (String range : ranges) {
                final String newRange = range.trim();
                if (newRange.contains("-")) {
                    final String[] bounds = newRange.split("-", 2);
                    if (bounds.length == 2 && DAY_MAP.containsKey(bounds[0]) && DAY_MAP.containsKey(bounds[1])) {
                        final int start = DAY_MAP.get(bounds[0]).getValue();
                        final int end = DAY_MAP.get(bounds[1]).getValue();
                        int i = start;
                        while (true) {
                            days.add(DayOfWeek.of(i));
                            if (i == end) {
                                break;
                            }
                            i = i % 7 + 1;
                        }
                    }
                } else if (DAY_MAP.containsKey(newRange)) {
                    days.add(DAY_MAP.get(newRange));
                }
            }
        }
        return days;
    }
}

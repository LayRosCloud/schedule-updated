package by.betrayal.audienceservice.core.utils.generator;

import by.betrayal.audienceservice.core.FakerUtils;
import by.betrayal.audienceservice.dto.event.EventCreateDto;
import by.betrayal.audienceservice.dto.event.EventUpdateDto;
import by.betrayal.audienceservice.entity.EventEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class EventGenerator {

    public static EventEntity generateEvent() {
        var date = new Date(2000, Calendar.NOVEMBER, 10);
        var faker = FakerUtils.FAKER;
        var corpus = new EventEntity();
        corpus.setValue(faker.name().title());
        corpus.setDateStart(date);
        corpus.setDateEnd(date);
        corpus.setAudience(AudienceGenerator.generateAudienceWithId());
        return corpus;
    }

    public static List<EventEntity> generateEventList(int count) {
        var events = new ArrayList<EventEntity>(count);

        for (int i = 0; i < count; i++) {
            events.add(generateEventWithId());
        }

        return events;
    }

    public static EventEntity generateEventWithId() {
        var faker = FakerUtils.FAKER;
        var corpus = generateEvent();
        corpus.setId(faker.random().nextLong(1, Long.MAX_VALUE));
        return corpus;
    }

    public static EventCreateDto generateCreateEvent(Long audienceId) {
        var faker = FakerUtils.FAKER;
        var date = new Date(2000, Calendar.NOVEMBER, 10);
        var corpus = EventCreateDto.builder()
                .value(faker.name().title())
                .audienceId(audienceId)
                .dateStart(date)
                .dateEnd(date)
                .build();

        return corpus;
    }

    public static EventUpdateDto generateUpdateEvent(Long id, Long audienceId) {
        var faker = FakerUtils.FAKER;
        var date = new Date(2000, Calendar.NOVEMBER, 10);
        var corpus = EventUpdateDto.builder()
                .id(id)
                .value(faker.name().title())
                .audienceId(audienceId)
                .dateStart(date)
                .dateEnd(date)
                .build();

        return corpus;
    }
}

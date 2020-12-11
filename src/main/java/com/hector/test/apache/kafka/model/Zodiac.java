package com.hector.test.apache.kafka.model;

import java.util.Calendar;

public enum Zodiac {
	Aries(21, Calendar.MARCH, 20, Calendar.APRIL,new String [] {"Leo","Sagittarius","Libra"}),
    Taurus(21, Calendar.APRIL, 21, Calendar.MAY,new String [] {"Capricorn","Cancer","Virgo"}),
    Gemini(22, Calendar.MAY, 21, Calendar.JUNE,new String [] {"Libra","Leo","Aries"}),
    Cancer(22, Calendar.JUNE, 22, Calendar.JULY,new String [] {"Pisces","Taurus","Scorpio"}),
    Leo(21, Calendar.JULY, 21, Calendar.AUGUST,new String [] {"Libra","Aries","Gemini"}),
    Virgo(22, Calendar.AUGUST, 23, Calendar.SEPTEMBER,new String [] {"Taurus","Cancer","Pisces"}),
    Libra(24, Calendar.SEPTEMBER, 23, Calendar.OCTOBER,new String [] {"Leo","Aquarius","Pisces"}),
    Scorpio(24, Calendar.OCTOBER, 22, Calendar.NOVEMBER,new String [] {"Pisces","Capricorn","Cancer"}),
    Sagittarius(23, Calendar.NOVEMBER, 22, Calendar.DECEMBER,new String [] {"Leo","Aries","Aquarius"}),
    Capricorn(23, Calendar.DECEMBER, 20, Calendar.JANUARY,new String [] {"Taurus","Virgo","Scorpio"}),
    Aquarius(21, Calendar.JANUARY, 19, Calendar.FEBRUARY,new String [] {"Sagittarius","Libra","Gemini"}),
    Pisces(20, Calendar.FEBRUARY, 20, Calendar.MARCH,new String [] {"Cancer","Scorpio","Taurus"});

    private final int dayFrom;
    private final int dayTo;
    private final int monthFrom;
    private final int monthTo;
    private final String [] compatibility;

    Zodiac(int dayFrom, int monthFrom, int dayTo, int monthTo,String[] compability) {
        this.dayFrom = dayFrom;
        this.monthFrom = monthFrom + 1;
        this.dayTo = dayTo;
        this.monthTo = monthTo + 1;
        this.compatibility = compability;
    }

    public static Zodiac get(int day, int month) {
        for (Zodiac sign : values())
            if (month == sign.monthFrom && day >= sign.dayFrom
                    || month == sign.monthTo && day <= sign.dayTo)
                return sign;

        throw new RuntimeException("Cannot select ZodiacSign");
    }

	public String [] getCompatibility() {
		return compatibility;
	}

}

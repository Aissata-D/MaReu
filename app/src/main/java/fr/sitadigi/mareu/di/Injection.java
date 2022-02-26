package fr.sitadigi.mareu.di;

import fr.sitadigi.mareu.service.MeetingApiServiceImplementation;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

public class Injection {
    public  static MeetingApiServiceInterface service = new MeetingApiServiceImplementation();

    public static MeetingApiServiceInterface getService() {
        return service;
    }

    public static MeetingApiServiceInterface getNewInstanceApiService() {
        return new MeetingApiServiceImplementation();
    }
}

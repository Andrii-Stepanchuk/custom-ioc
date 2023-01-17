package ua.stepanchuk;

import ua.stepanchuk.bean.NasaPicturesClient;
import ua.stepanchuk.context.CustomContext;

public class CustomIoCApp {
    public static void main(String[] args) {
        CustomContext context = new CustomContext();
        NasaPicturesClient nasaClient = context.getBean(NasaPicturesClient.class);
        nasaClient.getAllMarsRoverPhotos().forEach(System.out::println);
    }
}

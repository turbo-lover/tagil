package ru.news.tagil.utility;

/**
 * Created by turbo_lover on 23.07.13.
 */
public interface onClickInHeaderListener {
   abstract void UpdateButtonClicks();
   abstract void BackButtonClicks();
   abstract void SearchButtonClicks(String txt);
   abstract void UpdateWeather();
}

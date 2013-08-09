package ru.news.tagil.utility;

import ru.news.tagil.activity.*;

/**
 * Created by turbo_lover on 02.08.13.
 */
public enum firstClassesEnum {
       NewsPreview (activityNewsPreview.class.toString()),
       Message      (activityMessages.class.toString()),
       Favorite     (activityFavoriteNews.class.toString()),
       Useful       (activityUseful.class.toString()),
       Setting      (activitySettings.class.toString()),
       Correspondence  (activityCorrespondence.class.toString()),
       Contact      (activityContact.class.toString());

        private String typeValue;

        private firstClassesEnum(String type) {
            typeValue = type;
        }

        static public firstClassesEnum getClass (String pType) {
            for (firstClassesEnum type: firstClassesEnum.values()) {
                if (type.getTypeValue().equals(pType)) {
                    return type;
                }
            }
            throw new RuntimeException("unknown type");
        }

        public String getTypeValue() {
            return typeValue;
        }
}

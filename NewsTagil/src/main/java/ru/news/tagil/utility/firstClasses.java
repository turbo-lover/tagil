package ru.news.tagil.utility;

import ru.news.tagil.activity.*;

/**
 * Created by turbo_lover on 02.08.13.
 */
public enum firstClasses {
       NewsPreview(activityNewsPreview.class.toString()),
       Message      (activityMessages.class.toString()),
       Favorite     (activityFavoriteNews.class.toString()),
       Useful       (activityUseful.class.toString()),
       Setting      (activitySettings.class.toString());


        private String typeValue;

        private firstClasses(String type) {
            typeValue = type;
        }

        static public firstClasses getClass (String pType) {
            for (firstClasses type: firstClasses.values()) {
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

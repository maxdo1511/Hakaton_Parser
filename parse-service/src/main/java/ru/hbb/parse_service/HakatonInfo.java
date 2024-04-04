package ru.hbb.parse_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HakatonInfo {

    private String title;
    private String linkSite; // ссылка на сайт, где получена информация
    private String linkHakaton; // ссылка на регистрацию хакатон
    private String description;
    private long dateStart;
    private long dateEnd;
    private long dateRegisterStart; // если -1, то регистрация уже идёт
    private long dateRegisterEnd;

    @Override
    public String toString() {
        return "HakatonInfo{" +
                "title='" + title + '\'' +
                ", linkSite='" + linkSite + '\'' +
                ", linkHakaton='" + linkHakaton + '\'' +
                ", description='" + description + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", dateRegisterStart=" + dateRegisterStart +
                ", dateRegisterEnd=" + dateRegisterEnd +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HakatonInfo that)) return false;

        if (getDateStart() != that.getDateStart()) return false;
        if (getDateEnd() != that.getDateEnd()) return false;
        if (getDateRegisterStart() != that.getDateRegisterStart()) return false;
        if (getDateRegisterEnd() != that.getDateRegisterEnd()) return false;
        if (!getTitle().equals(that.getTitle())) return false;
        if (!getLinkSite().equals(that.getLinkSite())) return false;
        if (getLinkHakaton() != null ? !getLinkHakaton().equals(that.getLinkHakaton()) : that.getLinkHakaton() != null)
            return false;
        return getDescription() != null ? getDescription().equals(that.getDescription()) : that.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getLinkSite().hashCode();
        result = 31 * result + (getLinkHakaton() != null ? getLinkHakaton().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (int) (getDateStart() ^ (getDateStart() >>> 32));
        result = 31 * result + (int) (getDateEnd() ^ (getDateEnd() >>> 32));
        result = 31 * result + (int) (getDateRegisterStart() ^ (getDateRegisterStart() >>> 32));
        result = 31 * result + (int) (getDateRegisterEnd() ^ (getDateRegisterEnd() >>> 32));
        return result;
    }
}

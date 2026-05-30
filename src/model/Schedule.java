package model;

/**
 * Lịch học của một môn học
 * Ví dụ: Thứ 2, tiết 1-3
 */
public class Schedule {

    // Thu 2 = 2, Thu 3 = 3, ..., Thu 7 = 7, Chu nhat = 8
    private int dayOfWeek;

    // Tiet bat dau (1-12)
    private int startPeriod;

    // Tiet ket thuc (1-12)
    private int endPeriod;

    public Schedule(int dayOfWeek, int startPeriod, int endPeriod) {
        this.dayOfWeek = dayOfWeek;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }

    /**
     * Kiem tra xem lich nay co trung voi lich khac khong
     * Trung khi: cung thu va cac tiet bi chong len nhau
     */
    public boolean isConflict(Schedule other) {
        if (this.dayOfWeek != other.dayOfWeek) {
            return false;
        }
        return this.startPeriod <= other.endPeriod
                && other.startPeriod <= this.endPeriod;
    }

    // Getters
    public int getDayOfWeek()  { return dayOfWeek; }
    public int getStartPeriod() { return startPeriod; }
    public int getEndPeriod()   { return endPeriod; }

    // Setters
    public void setDayOfWeek(int dayOfWeek)    { this.dayOfWeek = dayOfWeek; }
    public void setStartPeriod(int startPeriod) { this.startPeriod = startPeriod; }
    public void setEndPeriod(int endPeriod)     { this.endPeriod = endPeriod; }

    @Override
    public String toString() {
        return "Thu " + dayOfWeek + ", tiet " + startPeriod + "-" + endPeriod;
    }
}
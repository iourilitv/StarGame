package gdx.stargame.lessons.lesson3.hw.math;


import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

/**
 * Утилита для работы с матрицами
 */
public class MatrixUtils {

    private MatrixUtils() {
    }

    /**
     * Расчёт матрицы перехода 4x4. Здесь только в 2D координатах.
     * @param mat итоговая матрица преобразований
     * @param src исходный квадрат
     * @param dst итоговый квадрат
     */
    public static void calcTransitionMatrix(Matrix4 mat, Rect src, Rect dst) {
        //расчитываем пропорции изменения размеров по X и Y
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        //расчитываем матрицу по следующей схеме(с лева на право)
        //приведем к нарисованной схеме
        //idt() - делаем матрицу единичной, на случай, если она была не нулевая
        //translate(...) - пересчитываем матрицу, чтобы сместить квадрат итоговый в начало координат
        //scale(...) - пересчитываем матрицу, чтобы пропорционально смасштабировать исходный квадрат
        //translate(...) - пересчитываем матрицу, чтобы сместить исходный квадрат в новые координаты
        mat.idt().translate(-src.pos.x, -src.pos.y, 0f).
                scale(scaleX, scaleY, 1f).translate(dst.pos.x, dst.pos.y, 0f);
    }

    /**
     * Расчёт матрицы перехода 3x3
     * @param mat итоговая матрица преобразований
     * @param src исходный квадрат
     * @param dst итоговый квадрат
     */
    public static void calcTransitionMatrix(Matrix3 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        mat.idt().translate(dst.pos.x, dst.pos.y).scale(scaleX, scaleY).translate(-src.pos.x, -src.pos.y);
    }
}

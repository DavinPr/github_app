import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class HorizontalLineDecoration(private val dividerDrawable: Drawable) :
    RecyclerView.ItemDecoration() {

    private val dividerWidth = dividerDrawable.intrinsicWidth
    private val dividerHeight = dividerDrawable.intrinsicHeight

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            parent.children.forEach { view ->
                val childAdapterPosition = parent.getChildAdapterPosition(view).let {
                    if (it == RecyclerView.NO_POSITION) return else it
                }
                if (childAdapterPosition != adapter.itemCount - 1) {
                    val left = view.right
                    val top = parent.paddingTop
                    val right = left + dividerWidth
                    val bottom = top + dividerHeight - parent.paddingBottom
                    dividerDrawable.bounds = Rect(left, top, right, bottom)
                    dividerDrawable.draw(c)
                }
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let { adapter ->
            val childAdapterPosition = parent.getChildAdapterPosition(view).let {
                if (it == RecyclerView.NO_POSITION) return else it
            }
            outRect.bottom =
                if (childAdapterPosition == adapter.itemCount - 1) 0 else dividerHeight
        }
    }
}
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.hackathon.huji.hujihackathon.Group
import com.hackathon.huji.hujihackathon.R
import com.hackathon.huji.hujihackathon.User
import com.hackathon.huji.hujihackathon.UsersAdapter


const val LOG_TAG = "GroupInfoDialogFragment"

class GroupInfoDialogFragment : DialogFragment() {

    private var title: TextView? = null
    private var list: ListView? = null
    private var group: Group? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState);
        if (arguments != null) {
            group = arguments!!.getParcelable("group")
            Log.d(LOG_TAG, "received group:" + group!!.name)
        }
        return inflater.inflate(R.layout.fragment_group_info, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (group == null) {
            throw NullPointerException("received null group!")
        }
        title = view.findViewById(R.id.group_title)
        title!!.text = group!!.name
        val lv = view.findViewById<View>(R.id.dialoglist) as ListView
        val adapter = UsersAdapter(
            this.context!!,
            ArrayList(group!!.members)
        )
        lv.adapter = adapter
    }

    companion object {
        fun newInstance(group: Group): GroupInfoDialogFragment {
            val frag = GroupInfoDialogFragment()
            val args = Bundle()
            args.putParcelable("group", group)
            frag.arguments = args
            return frag
        }
    }

}// Empty constructor is required for DialogFragment
// Make sure not to add arguments to the constructor
// Use `newInstance` instead as shown below
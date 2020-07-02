package com.codingblocks.cbonlineapp.mycourse.misc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingblocks.cbonlineapp.R
import com.codingblocks.cbonlineapp.commons.RoundedBottomSheetDialogFragment
import com.codingblocks.cbonlineapp.mycourse.MyCourseViewModel
import com.codingblocks.cbonlineapp.util.livedata.observeOnce
import kotlinx.android.synthetic.main.course_pause_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PauseFragment : RoundedBottomSheetDialogFragment() {

    val vm: MyCourseViewModel by sharedViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.course_pause_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pauseCancelBtn.setOnClickListener {
            dialog?.dismiss()
        }

        pauseCourseBtn.setOnClickListener {
            //Pause Course
            vm.pauseCourse().observeOnce { res ->
                if (res) {
                    requireActivity().finish()
                } else {
                    dialog?.dismiss()
                }
            }
        }

        pauseDescriptionTv.text
    }


}

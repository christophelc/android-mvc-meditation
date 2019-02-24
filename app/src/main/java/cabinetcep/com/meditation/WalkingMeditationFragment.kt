package cabinetcep.com.meditation

import android.content.Context
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import cabinetcep.com.meditation.databinding.FragmentWalkingMeditationBinding
import timber.log.Timber


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WalkingMeditationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WalkingMeditationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class WalkingMeditationFragment : PreferencesStorage() {

    private var localUserPreferences: LocalUserPreferences? = null
    private lateinit var timer: MeditationTimer
    private lateinit var binding: FragmentWalkingMeditationBinding
    private var ring : MediaPlayer? = null
    private var nDuration = 0
    //private var ringName = ""

    // TODO: Rename and change types of parameters
    //private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initSharedPreferences()
        initMediaPlayer()
        initTimer(savedInstanceState)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_walking_meditation, container, false)

        binding.stopButton.setOnClickListener { v: View ->
            timer.desactivate()
            timer.reset()
            v.findNavController().navigateUp()
        }
        timer.activate()
        return binding.root
        //return inflater.inflate(R.layout.fragment_walking_meditation, container, false)
    }

    private fun initSharedPreferences() {
        localUserPreferences = getUserPreferences()
        resources.getStringArray(R.array.sounds_array).indexOf(localUserPreferences?.ringName)
        // FIXME: calculate nDuration
        nDuration = resources.getStringArray(R.array.duration_array).indexOf(localUserPreferences?.duration) * 5
        // TODO: ringName -> id
    }

    private fun initMediaPlayer() {
        ring = MediaPlayer.create(this.context, R.raw.chime)
    }

    private fun initTimer(savedInstanceState: Bundle?) {
        val onFinished = {
            Timber.d("Timer ended")
            // si mis en commentaire: sonnerie périodique
            //timer.desactivate()
            timer.reset()
            playRing()
        }
        timer = MeditationTimer(lifecycle, nDuration, onFinished)
        if (savedInstanceState != null){
            timer.secondsCount = savedInstanceState.getInt(KEY_TIMER)
        }
        Timber.d("timer: ${timer.secondsCount}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("onSave")
        outState.putInt(KEY_TIMER, timer.secondsCount)
    }

    // FIXME: review code
    private fun playRing() {
        //val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val resMp3 = R.raw.chime
        this.ring?.release()
        this.ring = MediaPlayer.create(this.context, resMp3)
        //this.ring = MediaPlayer.create(this.context, uri)
        this.ring?.start()
    }


    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment WalkingMeditationFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            WalkingMeditationFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}

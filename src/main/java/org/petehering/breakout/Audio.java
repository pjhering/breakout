package org.petehering.breakout;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Audio
{
    private Map<String, Clip> clips;
    private Map<String, Sequence> midis;
    private Sequencer sequencer;
    
    public Audio () throws Exception
    {
        clips = new HashMap<> ();
        midis = new HashMap<> ();
        
        sequencer = MidiSystem.getSequencer ();
        sequencer.open ();
    }
    
    public void loadMidi (String path) throws Exception
    {
        try (InputStream stream = getClass ().getResourceAsStream (path))
        {
            Sequence seq = MidiSystem.getSequence (stream);
            midis.put (path, seq);
        }
    }
    
    public void startMidi (String path, int count)
    {
        if (midis.containsKey (path))
        {
            Sequence seq = midis.get (path);
            
            try
            {
                if (sequencer.isRunning ())
                {
                    sequencer.stop ();
                }
                
                sequencer.setSequence (seq);
                sequencer.setLoopCount (count);
                sequencer.setTickPosition (0);
                sequencer.start ();
            }
            catch (Exception ex)
            {
                ex.printStackTrace ();
            }
        }
    }
    
    public void stopMidi (String path)
    {
    }
    
    public void unloadMidi (String path)
    {
    }
    
    public void loadClip (String path) throws Exception
    {
        try (InputStream stream = getClass ().getResourceAsStream (path))
        {
            AudioInputStream input = AudioSystem.getAudioInputStream (stream);
            DataLine.Info info = new DataLine.Info (Clip.class, input.getFormat ());
            Clip clip = (Clip) AudioSystem.getLine (info);
            clip.open (input);
            clips.put (path, clip);
        }
    }
    
    public void playClip (String path)
    {
        if (clips.containsKey (path))
        {
            Clip clip = clips.get (path);
            
            if (!clip.isRunning ())
            {
                clip.setFramePosition (0);
                clip.start ();
            }
        }
    }
    
    public void unloadClip (String path)
    {
        if (clips.containsKey (path))
        {
            Clip clip = clips.remove (path);
            clip.close ();
        }
    }
    
    public void dispose ()
    {
        Set<String> keys = new HashSet<> ();
        
        keys.addAll (clips.keySet ());
        for (String key : keys)
        {
            unloadClip (key);
        }
        clips.clear ();
        keys.clear ();
        
        sequencer.stop ();
        sequencer.close ();
        keys.addAll (midis.keySet ());
        for (String key : keys)
        {
            unloadMidi (key);
        }
        midis.clear ();
        keys.clear ();
    }
}

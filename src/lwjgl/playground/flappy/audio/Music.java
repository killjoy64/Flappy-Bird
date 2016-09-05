package lwjgl.playground.flappy.audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import org.lwjgl.stb.STBVorbisInfo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC10.alcOpenDevice;
import static org.lwjgl.openal.ALC11.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.openal.AL;

/**
 * Created by John on 9/1/2016.
 */
public class Music implements Runnable {

    private static int source, buffer;

    public static Music MainGameplayMusic;

    private static boolean isPlaying;

    private static void play() {
        // generate buffers and sources
        int buffer = alGenBuffers();
        Music.buffer = buffer;
        checkALError();

        int source = alGenSources();
        Music.source = source;
        checkALError();

        try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
            ShortBuffer pcm = readVorbis("res/test.ogg", 32 * 1024, info);

            //copy to buffer
            alBufferData(buffer, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
            checkALError();
        }

        //set up source input
        alSourcei(source, AL_BUFFER, buffer);
        checkALError();

        //lets NOT loop the sound
        alSourcei(source, AL_LOOPING, AL_FALSE);
        checkALError();

        //play source 0
        alSourcePlay(source);
        checkALError();

        isPlaying = true;
    }
    public static void stop() {
        //stop source 0
        alSourceStop(source);
        checkALError();

        //delete buffers and sources
        alDeleteSources(source);
        checkALError();

        alDeleteBuffers(buffer);
        checkALError();
    }

    static ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) {
        ByteBuffer vorbis;
        try {
            vorbis = ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IntBuffer error = BufferUtils.createIntBuffer(1);
        long decoder = stb_vorbis_open_memory(vorbis, error, null);
        if ( decoder == NULL )
            throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));

        stb_vorbis_get_info(decoder, info);

        int channels = info.channels();

        int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

        ShortBuffer pcm = BufferUtils.createShortBuffer(lengthSamples);

        pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
        stb_vorbis_close(decoder);

        return pcm;
    }

    private static void checkALError() {
        int err = alGetError();
        if ( err != AL_NO_ERROR )
            throw new RuntimeException(alGetString(err));
    }

    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if ( Files.isReadable(path) ) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = createByteBuffer((int)fc.size() + 1);
                while ( fc.read(buffer) != -1 ) {
                    System.out.println(fc.read(buffer));
                }
            }
        } else {
            try (
                    InputStream source = Music.class.getClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while ( true ) {
                    int bytes = rbc.read(buffer);
                    if ( bytes == -1 ) {
                        break;
                    }
                    if ( buffer.remaining() == 0 )
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                }
            }
        }

        buffer.flip();
        return buffer;
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    @Override
    public void run() {
        try {

            long device = alcOpenDevice((ByteBuffer)null);
            if (device == NULL) {
                System.out.println("Failed to open audio device!");
            } else {
                System.out.println("Successfully opened audio device");
            }

            ALCCapabilities deviceCaps = ALC.createCapabilities(device);

            if (deviceCaps.OpenALC10 || deviceCaps.OpenALC11) {
                System.out.println("OpenAL support detected");
            } else {
                System.err.println("Your device does not support OpenAL 10 or OpenAL 11");
                alcCloseDevice(device);
            }

            long context = alcCreateContext(device, (IntBuffer)null);
            alcMakeContextCurrent(context);
            AL.createCapabilities(deviceCaps);

            play();

            boolean looping = true;
            int state = 0;
            /*while (looping) {
                try {
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }*/

            alcCloseDevice(device);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

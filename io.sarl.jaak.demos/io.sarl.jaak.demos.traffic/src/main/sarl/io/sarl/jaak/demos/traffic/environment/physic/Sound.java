package io.sarl.jaak.demos.traffic.environment.physic;

import io.sarl.jaak.demos.traffic.TrafficConstants;
import io.sarl.jaak.environment.endogenous.AutonomousEndogenousProcess;
import io.sarl.jaak.environment.influence.Influence;
import io.sarl.jaak.environment.perception.FloatSubstance;
import io.sarl.jaak.environment.perception.Substance;

import java.io.Serializable;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This class defines a sound.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class Sound extends FloatSubstance implements Cloneable, AutonomousEndogenousProcess {

	private static final long serialVersionUID = -7159194278058336621L;

	private Point2i soundSource;
	
	/**
	 * @param intensity the intensity of the sound.
	 * @param semantic the semantic of the sound.
	 */
	protected Sound(float intensity, Serializable semantic) {
		super(semantic);
		this.soundSource = null;
		setValue(intensity);
	}

	@Override
	public Sound clone() {
		Sound s = (Sound) super.clone();
		if (this.soundSource != null) {
			s.soundSource = this.soundSource.clone();
		}
		return s;
	}
	
	/** Replies a sound of the same type of this object, but with
	 * the given intensity and the source source position.
	 *
	 * @param intensity the intensity of the sound.
	 * @param soundSource the position of the sound source.
	 * @return
	 */
	Sound copy(float intensity, Point2i soundSource) {
		Sound s = clone();
		setValue(Math.max(0f, intensity));
		s.soundSource = soundSource.clone();
		return s;
	}
	
	/** Replies the position of the sound source.
	 *
	 * @return the position of the sound source.
	 */
	public Point2i getSoundSourcePosition() {
		if (this.soundSource == null) {
			return getPosition();
		}
		return this.soundSource.clone();
	}

	@Override
	protected final Substance decrement(Substance s) {
		if (s instanceof Sound) {
			float oldValue = floatValue();
			decrement(s.floatValue());
			Sound p = clone();
			p.setValue(Math.abs(floatValue() - oldValue));
			return p;
		}
		return null;
	}
	@Override
	protected Substance increment(Substance s) {
		if (s instanceof Sound) {
			Sound newSound = (Sound) s;
			float oldValue = floatValue();
			float v = Math.max(oldValue, s.floatValue());
			increment(v - oldValue);
			this.soundSource = newSound.getSoundSourcePosition();
			Sound p = clone();
			p.setValue(Math.abs(floatValue() - oldValue));
			return p;
		}
		return null;
	}

	@Override
	public Influence runAutonomousEndogenousProcess(float currentTime, float simulationStepDuration) {
		decrement(TrafficConstants.SOUND_DECREASE);
		if (floatValue() <= 0f) {
			return createRemovalInfluenceForItself();
		}
		return null;
	}

}
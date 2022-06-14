import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;


public class KolokwiumPOiGK extends JFrame{

    KolokwiumPOiGK(){ 
        super("Grafika 3D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(1200,800));

        add(canvas3D);
        pack();
        setVisible(true);

        BranchGroup scena = utworzScene();
	scena.compile();

        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,16.0f));

        simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

        simpleU.addBranchGraph(scena);
    }

    BranchGroup utworzScene(){

        BranchGroup wezel_scena = new BranchGroup();

    //OBRÓT ELEMENTÓW
          TransformGroup obrot_animacja = new TransformGroup();
          obrot_animacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
          wezel_scena.addChild(obrot_animacja);

          Alpha alpha_animacja = new Alpha(-1,5000);

          Transform3D os_obrotu = new Transform3D();
          os_obrotu.set(new Vector3f(0.0f,0.0f,0.0f));

          RotationInterpolator obracacz = new RotationInterpolator(alpha_animacja, obrot_animacja, os_obrotu, 0.0f , 2*(float)Math.PI);

          BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
          obracacz.setSchedulingBounds(bounds);
          obrot_animacja.addChild(obracacz);

    //ŚWIATŁA
          AmbientLight lightA = new AmbientLight();
          lightA.setInfluencingBounds(bounds);
          wezel_scena.addChild(lightA);

          BoundingSphere obszar_ogr =  new BoundingSphere(new Point3d(0.0d,0.0d,0.0d), 10.0d);
          PointLight swiatlo_pnkt = new PointLight(new Color3f(8.0f, 8.0f, 8.0f), new Point3f(2.0f,2.0f,0.0f), new Point3f(0.5f,0.5f,0.5f));
          swiatlo_pnkt.setInfluencingBounds(obszar_ogr);    
          wezel_scena.addChild(swiatlo_pnkt);

    //KULE
          // materiał obu kul
          Material material_kuli = new Material(new Color3f(0.0f, 0.0f,0.5f), new Color3f(0.0f,0.0f,0.0f),
                                                new Color3f(0.0f, 1f, 0.0f), new Color3f(2.0f, 2.0f, 2.0f), 80.0f);

          ColoringAttributes cattr = new ColoringAttributes();
          cattr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);

          Appearance wygladKuli = new Appearance();
          wygladKuli.setMaterial(material_kuli);
          wygladKuli.setColoringAttributes(cattr);

          // tworzenie kuli 1
          Sphere kula = new Sphere(0.5f, Sphere.GENERATE_NORMALS, 80, wygladKuli);   

          Transform3D p_kuli = new Transform3D();
          p_kuli.set(new Vector3f(-2.0f,0.0f,0.0f));     //położenie kuli

          TransformGroup transformacja_k = new TransformGroup(p_kuli);
          transformacja_k.addChild(kula);
          obrot_animacja.addChild(transformacja_k);     // obracanie kuli

          // tworzenie kuli 2
          Sphere kula2 = new Sphere(0.5f, Sphere.GENERATE_NORMALS, 80, wygladKuli);

          Transform3D p_kuli2 = new Transform3D();
          p_kuli2.set(new Vector3f(2.0f,0.0f,0.0f));     //położenie kulki

          TransformGroup transformacja_k2 = new TransformGroup(p_kuli2);
          transformacja_k2.addChild(kula2);
          obrot_animacja.addChild(transformacja_k2);

    //WALEC
          // tworzenie walca
          Cylinder walec = new Cylinder(0.1f,4f, wygladKuli);

          Transform3D p_walca = new Transform3D();
          p_walca.set(new Vector3f(0.0f,0.0f,0.0f));

          // obrót walca o 90deg
          Transform3D tmp_rot = new Transform3D();
          tmp_rot.rotZ(-Math.PI/2);
          p_walca.mul(tmp_rot);

          TransformGroup transformacja_w = new TransformGroup(p_walca); 
          transformacja_w.addChild(walec);
          obrot_animacja.addChild(transformacja_w);

          return wezel_scena;
    }

    public static void main(String args[]){
        new KolokwiumPOiGK();
    }
}



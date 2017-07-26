package main.java.org.cytoscape.pokemeow.internal.edge;

import com.jogamp.opengl.GL4;
import main.java.org.cytoscape.pokemeow.internal.algebra.Vector3;
import main.java.org.cytoscape.pokemeow.internal.algebra.Vector4;
import main.java.org.cytoscape.pokemeow.internal.arrowshape.pmBasicArrowShape;
import main.java.org.cytoscape.pokemeow.internal.line.pmLineVisual;
import main.java.org.cytoscape.pokemeow.internal.line.pmLineFactory;
import main.java.org.cytoscape.pokemeow.internal.arrowshape.pmArrowShapeFactory;
import main.java.org.cytoscape.pokemeow.internal.rendering.pmShaderParams;

/**
 * Created by ZhangMenghe on 2017/7/25.
 */
public class pmEdge {
    private pmLineVisual _line;
    private pmBasicArrowShape _srcArrow;
    private pmBasicArrowShape _destArrow;
    private pmLineFactory lineFactory;
    private pmArrowShapeFactory arrowFactory;
    public Byte curveType;
    private float xMin,xMax,yMin,yMax;
    private Vector3 origin;
    public pmEdge(GL4 gl4, Byte lineType, Byte mcurveType,
                  float srcx, float srcy, float destx, float desty){
        lineFactory = new pmLineFactory(gl4);
        _line = lineFactory.createLine(lineType, srcx, srcy, destx, desty,mcurveType);
        curveType = mcurveType;
        xMin = Math.min(srcx, destx); xMax = Math.max(srcx, destx);
        yMin = Math.min(srcy, desty); yMax = Math.max(srcy, desty);
        origin = new Vector3((srcx+destx)/2, (srcy+desty)/2, _line.zorder);
    }
    public pmEdge(GL4 gl4, Byte lineType, Byte mcurveType,Byte destArrowType,
                  float srcx, float srcy, float destx, float desty){
        lineFactory = new pmLineFactory(gl4);
        _line = lineFactory.createLine(lineType, srcx, srcy, destx, desty,mcurveType);
        curveType = mcurveType;
        xMin = Math.min(srcx, destx)-0.01f; xMax = Math.max(srcx, destx)+0.01f;
        yMin = Math.min(srcy, desty)-0.01f; yMax = Math.max(srcy, desty)+0.01f;
        arrowFactory = new pmArrowShapeFactory(gl4);
        _destArrow = arrowFactory.createArrow(destArrowType);
        setArrowRotation();
        origin = new Vector3((srcx+destx)/2, (srcy+desty)/2, _line.zorder);
    }
    public pmEdge(GL4 gl4, Byte lineType, Byte mcurveType, Byte srcArrowType, Byte destArrowType,
                  float srcx, float srcy, float destx, float desty){
        lineFactory = new pmLineFactory(gl4);
        _line = lineFactory.createLine(lineType, srcx, srcy, destx, desty,mcurveType);
        curveType = mcurveType;
        xMin = Math.min(srcx, destx)-0.01f; xMax = Math.max(srcx, destx)+0.01f;
        yMin = Math.min(srcy, desty)-0.01f; yMax = Math.max(srcy, desty)+0.01f;
        arrowFactory = new pmArrowShapeFactory(gl4);
        _srcArrow = arrowFactory.createArrow(srcArrowType);
        _destArrow = arrowFactory.createArrow(destArrowType);
        setArrowRotation();
        origin = new Vector3((srcx+destx)/2, (srcy+desty)/2, _line.zorder);
    }

    public pmEdge(GL4 gl4, pmLineVisual line, pmBasicArrowShape srcArrow, pmBasicArrowShape destArrow){
        _line = line;
        curveType = line.curveType;
        xMin = Math.min(line.srcPos.x, line.destPos.x)-0.01f;
        xMax = Math.max(line.srcPos.x, line.destPos.x)+0.01f;
        yMin = Math.min(line.srcPos.y, line.destPos.y)-0.01f;
        yMax = Math.max(line.srcPos.y, line.destPos.y)+0.01f;
        _srcArrow = srcArrow;
        _destArrow = destArrow;
        lineFactory = new pmLineFactory(gl4);
        if(srcArrow==null && destArrow==null)
            return;

        arrowFactory = new pmArrowShapeFactory(gl4);
        setArrowRotation();
        origin = new Vector3((line.srcPos.x+line.destPos.x)/2, (line.srcPos.y+line.destPos.y)/2, _line.zorder);
    }

    private void setArrowRotation(){
        double thetasrc, thetadest;
        if(_line.curveType == pmLineVisual.LINE_STRAIGHT){
            thetasrc = Math.atan(_line.slope);
            if(_line.slope<0)
                thetasrc -= 3.14f;
            thetadest = thetasrc;
        }
        else{
            float k = (_line.srcPos.y - _line.controlPoints[1]) / (_line.srcPos.x - _line.controlPoints[0]);
            thetasrc = Math.atan(k);
            float k2 = (_line.controlPoints[1]-_line.destPos.y) / (_line.controlPoints[0]-_line.destPos.x);
            thetadest= Math.atan(k2);
//            if(Math.abs(k2)>=1)
//                thetadest += 3.14f;
        }

        if(_srcArrow != null){
            _srcArrow.setOrigin(new Vector3(_line.srcPos.x, _line.srcPos.y, _line.zorder));
            _srcArrow.setRotation((float) thetasrc-3.14f);
            _srcArrow.setScale(0.2f);
        }

        if(_destArrow != null){
            _destArrow.setOrigin(new Vector3(_line.destPos.x, _line.destPos.y, _line.zorder));
            _destArrow.setScale(0.2f);
            _destArrow.setRotation((float) thetadest);
        }
    }
    public void draw(GL4 gl4, pmShaderParams gshaderParam){
        if(_srcArrow != null)
            arrowFactory.drawArrow(gl4, _srcArrow, gshaderParam);
        if(_destArrow != null)
            arrowFactory.drawArrow(gl4, _destArrow, gshaderParam);
        lineFactory.drawLine(gl4, _line, gshaderParam);
    }

    public void setControlPoints(float nctrx, float nctry, int anchorID){
        _line.setControlPoints(nctrx, nctry, anchorID);
        double theta;
        if(_srcArrow!=null){
            float k = (_line.srcPos.y - _line.controlPoints[1]) / (_line.srcPos.x - _line.controlPoints[0]);
            theta = Math.atan(k);
            _srcArrow.setRotation((float) theta-3.14f);
        }
        if(_destArrow!=null){
            float k2 = (_line.controlPoints[1]-_line.destPos.y) / (_line.controlPoints[0]-_line.destPos.x);
            theta = Math.atan(k2);
//            if(Math.abs(k2)>=100)
//                theta += 3.14f;
            _destArrow.setRotation((float) theta);
        }
    }

    public boolean isAnchorHit(float posx, float posy, int anchorID){
        if(anchorID == 1)
            return _line.anchor.isHit(posx,posy);
        else
            return  ( _line.anchor2!=null && _line.anchor2.isHit(posx,posy));
    }

    public void dispose(GL4 gl4){
        _line.dispose(gl4);
        if(_srcArrow!=null)
            _srcArrow.dispose(gl4);
        if(_destArrow!=null)
            _destArrow.dispose(gl4);
    }

    public boolean isHit(float posx, float posy){
        if(_line.curveType == pmLineVisual.LINE_STRAIGHT)
            return isHitStraightLine(posx, posy);
//        float bounxMin = xMin; float boundxMax = xMax;
//        float boundyMin = yMin; float boundyMax = yMax;

        xMin = Math.min(xMin, _line.controlPoints[0]); xMax = Math.max(xMax,  _line.controlPoints[0]);
        yMin = Math.min(yMin, _line.controlPoints[1]); yMax = Math.max(yMax,  _line.controlPoints[1]);
        if(_line.curveType == pmLineVisual.LINE_CUBIC_CURVE){
            xMin = Math.min(xMin, _line.controlPoints[2]); xMax = Math.max(xMax,  _line.controlPoints[2]);
            yMin = Math.min(yMin, _line.controlPoints[3]); yMax = Math.max(yMax,  _line.controlPoints[3]);
        }
        if(posx<xMin || posx>xMax || posy<yMin || posy>yMax)
            return false;
//        float tmpx = posx-(bounxMin+boundxMax)/2;
//        float tmpy = posy-(boundyMin+boundyMax)/2;
        if(_line.slope>0){
            for(int i=0; i<_line.numOfVertices-1; i++){
                if(_line.vertices[3*i+1]<(posy-0.02f) || _line.vertices[3*i] > (posx+0.02f))
                    continue;
                float deltay = _line.vertices[3*i+1]-posy;
                float deltax = _line.vertices[3*i]-posx;
                float length =deltay*deltay + deltax*deltax;

//                float deltay = _line.vertices[3*(i+1)+1] - _line.vertices[3*i+1];
//                float deltax = _line.vertices[3*(i+1)] - _line.vertices[3*i];
//                float k = deltay / deltax;//
//                float length = Math.abs(tmpx+k*tmpy-k*liney-linex) / (float)Math.sqrt(k * k +1);
                if(length <= 0.0002f)
                    return true;

            }
        }
        else{
            for(int i=0; i<_line.numOfVertices-1; i++){
                if(_line.vertices[3*i]<(posx-0.02f) || _line.vertices[3*i+1] > (posy+0.02f))
                    continue;
                float deltay = _line.vertices[3*i+1]-posy;
                float deltax = _line.vertices[3*i]-posx;
                float length =deltay*deltay + deltax*deltax;
                if(length <= 0.0002f)
                    return true;

            }
        }
        return false;
    }
    private boolean isHitStraightLine(float posx, float posy){
        if(posx<xMin || posx>xMax || posy<yMin || posy>yMax)
            return false;
        float tmpx = posx-(xMin+xMax)/2;
        float tmpy = posy-(yMin+yMax)/2;
        float length = Math.abs(_line.slope*tmpx - tmpy) / (float)Math.sqrt(_line.slope * _line.slope +1);
        if(length <= 0.005f)
            return true;
        else
            return false;
    }
    public void setColor(Vector4 ncolor){
        _line.setColor(ncolor);
        if(_srcArrow!=null)
            _srcArrow.setColor(ncolor);
        if(_destArrow!=null)
            _destArrow.setColor(ncolor);
    }
    public void setRotation(float radians){
        _line.setRotation(radians);
        if(_srcArrow!=null)
            _srcArrow.setRotation(radians);
        if(_destArrow!=null)
            _destArrow.setRotation(radians);
    }
    public void setOrigin(Vector3 new_origin){
        float deltax = new_origin.x - origin.x;
        float deltay = new_origin.y - origin.y;
        origin.x = new_origin.x;
        origin.y = new_origin.y;

    }
}

from PIL import Image
import numpy as np
import sys

im = Image.open(sys.argv[1])

na = np.array(im)
na[np.mean(na, axis=-1) < 80] = (0, 0, 0)

result = Image.fromarray(na)
result.save(sys.argv[1], format='JPEG')

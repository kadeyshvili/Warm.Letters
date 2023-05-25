from bs4 import BeautifulSoup
import sys

js_script = """
          $(function() {{
     var paths = $("path");
       var tl = new TimelineMax();
        paths.each(function(i, e) {{
          e.style.strokeDasharray = e.style.strokeDashoffset = e.getTotalLength();
        }});
        tl.staggerTo(
          paths,
          0.2,
          {{ strokeDashoffset: 0, stroke: "{}", strokeWidth: 20 }},
          0.2
        );
    }}); 
""".format("#000")

def sort_paths_by_x(path_elements, paths_ends, path, soup):
    parent_element = soup.find('g')
    prev = 0
    if paths_ends:
        for i in range(len(paths_ends) + 1):
            if i == 0:
                path_elements_cur = path_elements[:paths_ends[i]]
                prev = paths_ends[i]
            elif i == len(paths_ends):
                path_elements_cur = path_elements[prev:]
            else:
                path_elements_cur = path_elements[prev:paths_ends[i]]
                prev = paths_ends[i]

            sorted_path_elements = sorted(path_elements_cur,
                                          key=lambda x: [float(x.get('d').split('M')[1].split()[0]),
                                                         float(x.get('d').split('M')[1].split()[1])])
            for path_element in sorted_path_elements:
                parent_element.append(path_element)
    else:
        sorted_path_elements = sorted(path_elements, key=lambda x: [float(x.get('d').split('M')[1].split()[0]),
                                                                    float(x.get('d').split('M')[1].split()[1])])
        for path_element in sorted_path_elements:
            parent_element.append(path_element)

    script_1 = soup.new_tag('script', src="'https://cdnjs.cloudflare.com/ajax/libs/gsap/1.19.1/TweenMax.min.js'")
    script_2 = soup.new_tag('script', src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js")
    script_3 = soup.new_tag('script', src="http://cdnjs.cloudflare.com/ajax/libs/gsap/1.14.2/TweenMax.min.js")
    script_4 = soup.new_tag('script', src="https://cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.3/ScrollMagic.js")
    script_5 = soup.new_tag('script',
                            src="https://cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.3/plugins/animation.gsap.js")
    script_6 = soup.new_tag('script',
                            src="https://cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.3/plugins/debug.addIndicators.js")
    main_script = soup.new_tag('script')
    main_script.string = js_script
    svg_tag.insert_after(script_1, script_2, script_3, script_4, script_5, script_6, main_script)
    with open(path, "w") as f:
        f.write(str(soup))



def get_indexes_of_new_line(path_tags):
    mas_of_diff_y = []
    for j in range(0, len(path_tags) - 1):
        left_y = float(path_tags[j].get('d').split('M')[1].split()[1])
        right_y = float(path_tags[j + 1].get('d').split('M')[1].split()[1])
        mas_of_diff_y.append(abs(left_y - right_y))
    mas_of_diff_y = sorted(mas_of_diff_y)
    line_spacing = sum(mas_of_diff_y)/len(mas_of_diff_y)
    line_spacing *= 3
    indexes_of_new_line = []
    for j in range(0, len(path_tags) - 1):
        left_y = float(path_tags[j].get('d').split('M')[1].split()[1])
        right_y = float(path_tags[j + 1].get('d').split('M')[1].split()[1])
        if abs(left_y - right_y) >= line_spacing:
            indexes_of_new_line.append(j)
    return indexes_of_new_line


with open(sys.argv[1], "r") as f:
    html_content = f.read()

soup = BeautifulSoup(html_content, "html.parser")

path_tags = soup.find_all('path')
svg_tag = soup.find('svg')
g_tag = soup.find('g')

svg_tag['width'] = '1000pt'
svg_tag['height'] = '700pt'

g_tag['fill'] = "none"

indexes = get_indexes_of_new_line(path_tags)
sort_paths_by_x(path_tags, indexes, sys.argv[1], soup)











